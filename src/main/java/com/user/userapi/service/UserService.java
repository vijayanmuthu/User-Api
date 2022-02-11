package com.user.userapi.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.client.RestTemplate;

import com.user.userapi.Entity.Userdetail;
import com.user.userapi.repo.UserdetailRepo;
import com.user.userapi.valueObject.PageDetail;
import com.user.userapi.valueObject.UserdetailVo;

@Service
@EnableScheduling
public class UserService {
	@Autowired
	UserdetailRepo userdetailRepo;

	@Value("${url}")
	private String url;

	@Scheduled(fixedRate = 5000)
	public void getuserDetails() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PageDetail> userResponseEntity = restTemplate.getForEntity(url, PageDetail.class);
		this.postUser(userResponseEntity.getBody().data);
	}

	public void postUser(List<UserdetailVo> userdetailVos) {

		ArrayList<Userdetail> user = new ArrayList<>();
		Map<String, Object> remoteusermap = new HashMap<>();
		for (UserdetailVo uservo : userdetailVos) {
			remoteusermap.put(uservo.getEmail(), uservo);
		}
		List<Userdetail> userdetails = userdetailRepo.findByEmailIn(remoteusermap.keySet());
		Map<String, Object> dbmap = new HashMap<>();
		for (Userdetail uservo : userdetails) {
			dbmap.put(uservo.getEmail(), uservo);
		}

		for (UserdetailVo userdetailVo : userdetailVos) {
			if (!dbmap.containsKey(userdetailVo.getEmail())) {
				Userdetail userDetail = new Userdetail();
				userDetail.setAvatar(userdetailVo.getAvatar());
				userDetail.setEmail(userdetailVo.getEmail());
				userDetail.setFirst_name(userdetailVo.getFirstName());
				userDetail.setLast_name(userdetailVo.getLastName());
				user.add(userDetail);
				dbmap.put(userDetail.getEmail(), userDetail);
			} else {
				Userdetail userDetail = new Userdetail();
				userDetail.setFirst_name(userdetailVo.getFirstName());
				userDetail.setLast_name(userdetailVo.getLastName());
				userDetail.setAvatar(userdetailVo.getAvatar());
				userdetailRepo.save(userDetail);
			}
		}
		userdetailRepo.saveAll(user);
	}

	@Transactional
	public Userdetail NewUser(UserdetailVo userdetail)
			throws RuntimeException, SQLIntegrityConstraintViolationException {
		if (userdetail.getEmail().equals(null)) {
			throw new NullPointerException("invalid Email");
		}
		try {
			Userdetail user = new Userdetail();
			user.setAvatar(userdetail.getAvatar());
			user.setEmail(userdetail.getEmail());
			user.setFirst_name(userdetail.getFirstName());
			user.setLast_name(userdetail.getLastName());
			userdetailRepo.save(user);
			return user;
		} catch (UnexpectedRollbackException e) {
			throw new SQLIntegrityConstraintViolationException();
		}
	}

	public Userdetail getUser(String email) {

		Userdetail user = userdetailRepo.findByEmail(email);
		return user;
	}
}
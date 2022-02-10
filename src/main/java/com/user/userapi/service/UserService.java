package com.user.userapi.service;

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
import com.user.userapi.model.PageDetail;
import com.user.userapi.repo.UserdetailRepo;
import com.user.userapi.valueObject.UserdetailVo;

@Service
@EnableScheduling
public class UserService {
	@Autowired
	UserdetailRepo userdetailRepo;
	@Value("${url}")
	private String url;

	@Scheduled(fixedRate = 5000)
	public ResponseEntity<PageDetail> getuserDetails() {
		// String uri = "https://reqres.in/api/users";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PageDetail> userResponseEntity = restTemplate.getForEntity(url, PageDetail.class);
		this.postUser(userResponseEntity.getBody().data);
		return userResponseEntity;
	}

	public List<Userdetail> postUser(List<UserdetailVo> userdetail) {
		ArrayList<Userdetail> user = new ArrayList<>();
		Map<String, Object> reqresdbmap = new HashMap<>();
		for (UserdetailVo uservo : userdetail) {
			reqresdbmap.put(uservo.getEmail(), uservo);
		}
		List<Userdetail> getemail = userdetailRepo.findByEmailIn(reqresdbmap.keySet());
		Map<String, Object> dbmap = new HashMap<>();
		for (Userdetail uservo : getemail) {
			dbmap.put(uservo.getEmail(), uservo);
		}

		for (UserdetailVo userdetailVo : userdetail) {
			if (dbmap.containsKey(userdetailVo.getEmail()) == false) {
				Userdetail userDetail = new Userdetail();
				userDetail.setAvatar(userdetailVo.getAvatar());
				userDetail.setEmail(userdetailVo.getEmail());
				userDetail.setFirst_name(userdetailVo.getFirst_name());
				userDetail.setLast_name(userdetailVo.getLast_name());
				user.add(userDetail);
			}
		}
		userdetailRepo.saveAll(user);
		return user;
	}

	@Transactional
	public Userdetail NewUser(UserdetailVo userdetail) throws Exception {

		try {
			Userdetail user = new Userdetail();
			user.setAvatar(userdetail.getAvatar());
			user.setEmail(userdetail.getEmail());
			user.setFirst_name(userdetail.getFirst_name());
			user.setLast_name(userdetail.getLast_name());
			userdetailRepo.save(user);
			return user;
		} catch (UnexpectedRollbackException e) {
			throw new Exception("");
		}

	}

	public Userdetail getUser(String email) {
		Userdetail user = userdetailRepo.findByEmail(email);
		return user;
	}
}
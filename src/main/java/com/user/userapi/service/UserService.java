package com.user.userapi.service;

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
import org.springframework.web.client.RestTemplate;

import com.user.userapi.Entity.Userdetail;
import com.user.userapi.exception.EmailFoundException;
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

	@Transactional
	@Scheduled(fixedRate = 5000)
	public ResponseEntity<PageDetail> getuserDetails() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PageDetail> userResponseEntity = restTemplate.getForEntity(url, PageDetail.class);
		this.postUser(userResponseEntity.getBody().data);
		return userResponseEntity;
	}

	public void postUser(List<UserdetailVo> userdetailVos) {

		Map<String, Object> remoteusermap = new HashMap<>();
		for (UserdetailVo uservo : userdetailVos) {
			remoteusermap.put(uservo.getEmail(), uservo);
		}
		List<Userdetail> userdetails = userdetailRepo.findByEmailIn(remoteusermap.keySet());
		Map<String, Userdetail> dbmap = new HashMap<>();
		for (Userdetail uservo : userdetails) {
			dbmap.put(uservo.getEmail(), uservo);
		}
		for (UserdetailVo userdetailVo : userdetailVos) {
			final String email = String.valueOf(userdetailVo.getEmail());
			final String avatar = String.valueOf(userdetailVo.getAvatar());
			final String firstName = String.valueOf(userdetailVo.getFirst_name());
			final String lastname = String.valueOf(userdetailVo.getLast_name());
			Userdetail userdetail = dbmap.get(email);
			if (userdetail == null) {
				userdetail = new Userdetail();
				userdetail.setEmail(String.valueOf(email));
			}
			userdetail.setAvatar(String.valueOf(avatar));
			userdetail.setFirst_name(firstName);
			userdetail.setLast_name(lastname);
			dbmap.put(userdetail.getEmail(), userdetail);
		}
		userdetailRepo.saveAll(dbmap.values());
	}

	@Transactional
	public Userdetail NewUser(UserdetailVo userdetail) {
		
		if (userdetail.getEmail() == null) {
			throw new com.user.userapi.exception.ValidationException("Email is Null");
		}
		Userdetail userDetailByemail = userdetailRepo.findByEmail(userdetail.getEmail());
		if (userDetailByemail != null) {
			throw new EmailFoundException("Email Found Exception");
		}
		Userdetail user = new Userdetail();
		user.setAvatar(userdetail.getAvatar());
		user.setEmail(userdetail.getEmail());
		user.setFirst_name(userdetail.getFirst_name());
		user.setLast_name(userdetail.getLast_name());
		userdetailRepo.save(user);
		return user;
	}

	public Userdetail getUser(String email) {

		Userdetail user = userdetailRepo.findByEmail(email);
		return user;
	}
}
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
import com.user.userapi.config.WebSecurityConfig;
import com.user.userapi.exception.EmailFoundException;
import com.user.userapi.exception.EmailValidationException;
import com.user.userapi.repo.UserdetailRepo;
import com.user.userapi.valueObject.PageDetail;
import com.user.userapi.valueObject.UserVo;
import com.user.userapi.valueObject.UserdetailVo;

@Service
@EnableScheduling
public class UserService {
	@Autowired
	UserdetailRepo userdetailRepo;

	@Autowired
	WebSecurityConfig webSecurityConfig;
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

	public void postUser(List<UserdetailVo> userdetailVoList) {

		Map<String, Object> remoteusermap = new HashMap<>();
		for (UserdetailVo uservo : userdetailVoList) {
			remoteusermap.put(uservo.getEmail(), uservo);
		}
		List<Userdetail> userdetails = userdetailRepo.findByEmailIn(remoteusermap.keySet());
		Map<String, Userdetail> dbmap = new HashMap<>();
		for (Userdetail userdetail : userdetails) {
			dbmap.put(userdetail.getEmail(), userdetail);
		}
		for (UserdetailVo userdetailVo : userdetailVoList) {
			final String email = String.valueOf(userdetailVo.getEmail());
			final String avatar = String.valueOf(userdetailVo.getAvatar());
			final String firstName = String.valueOf(userdetailVo.getFirst_name());
			final String lastname = String.valueOf(userdetailVo.getLast_name());
			String password = "password";
			Userdetail userdetail = dbmap.get(email);
			if (userdetail == null) {
				userdetail = new Userdetail();
				userdetail.setEmail(String.valueOf(email));
				userdetail.setPassword(webSecurityConfig.passwordEncoder().encode(password));
			}
			userdetail.setAvatar(String.valueOf(avatar));    
			userdetail.setFirst_name(firstName);
			userdetail.setLast_name(lastname);
			dbmap.put(userdetail.getEmail(), userdetail);
		}
		userdetailRepo.saveAll(dbmap.values());
	}

	@Transactional
	public UserVo NewUser(UserdetailVo userdetailVo) {

		if (userdetailVo.getEmail() == null) {
			throw new EmailValidationException("Email not found");
		}
		Userdetail userDetailByemail = userdetailRepo.findByEmail(userdetailVo.getEmail());
		if (userDetailByemail != null) {
			throw new EmailFoundException("Email Exist Exception");
		}
		Userdetail user = new Userdetail();
		user.setAvatar(userdetailVo.getAvatar());
		user.setEmail(userdetailVo.getEmail());
		user.setFirst_name(userdetailVo.getFirst_name());
		user.setLast_name(userdetailVo.getLast_name());
		user.setPassword(webSecurityConfig.passwordEncoder().encode(userdetailVo.getPassword()));
		userdetailRepo.save(user);
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setAvatar(user.getAvatar());
		userVo.setEmail(user.getEmail());
		userVo.setFirst_name(user.getFirst_name());
		userVo.setLast_name(user.getLast_name());
		return userVo;
	}

	public UserVo getUser(String email) {

		Userdetail user = userdetailRepo.findByEmail(email);
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setAvatar(user.getAvatar());
		userVo.setEmail(user.getEmail());
		userVo.setFirst_name(user.getFirst_name());
		userVo.setLast_name(user.getLast_name());
		return userVo;
	}
}
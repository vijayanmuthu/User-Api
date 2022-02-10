package com.user.userapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.userapi.Entity.Userdetail;
import com.user.userapi.model.PageDetail;
import com.user.userapi.repo.UserdetailRepo;
import com.user.userapi.valueObject.UserdetailVo;

@Service
public class UserService {
	@Autowired
	UserdetailRepo userdetailRepo;

	public ResponseEntity<PageDetail> getuserDetails() {
		String uri = "https://reqres.in/api/users";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PageDetail> userResponseEntity = restTemplate.getForEntity(uri, PageDetail.class);
		this.postUser(userResponseEntity.getBody().data);
		return userResponseEntity;
	}

	public List<Userdetail> postUser(List<UserdetailVo> userdetail) {
		ArrayList<Userdetail> user = new ArrayList<>();
		List<UserdetailVo> userdetailvo = new ArrayList<>();
		userdetailvo.addAll(userdetail);
		Map<String, Object> reqresdbmap = new HashMap<>();
		for (UserdetailVo uservo : userdetailvo) {
			reqresdbmap.put(uservo.getEmail(), uservo);
		}
		List<Object> getemail = userdetailRepo.findEmail( reqresdbmap.keySet());
		Map<Object, Object> dbmap = new HashMap<>();
		for (Object uservo : getemail) {
			dbmap.put(uservo, uservo);
		}
		System.out.println("reqres db : " + reqresdbmap);
		System.out.println("Our db : " + dbmap);
		for (UserdetailVo userdetailvo1 : userdetailvo) {
			if (dbmap.containsKey(userdetailvo1.getEmail())) {
				System.out.println(userdetailvo1.getEmail()+" already exist");
			} else {
				Userdetail userdetail1 = new Userdetail();
				userdetail1.setAvatar(userdetailvo1.getEmail());
				userdetail1.setEmail(userdetailvo1.getEmail());
				userdetail1.setFirst_name(userdetailvo1.getFirst_name());
				userdetail1.setLast_name(userdetailvo1.getLast_name());
				user.add(userdetail1);
			}
		}
//		for (int i = 0; i < userdetail.size(); i++) {
//			if (dbmaps.containsKey(userdetailvo.get(i).getEmail())) {
//				System.out.println("email is already exist");
//
//			} else {
//				Userdetail userdetail1 = new Userdetail();
//				userdetail1.setAvatar(userdetail.get(i).getAvatar());
//				userdetail1.setEmail(userdetail.get(i).getEmail());
//				userdetail1.setFirst_name(userdetail.get(i).getFirst_name());
//				userdetail1.setLast_name(userdetail.get(i).getLast_name());
//				user.add(userdetail1);
//			}
//		}

		userdetailRepo.saveAll(user);
		return user;
	}

	public Userdetail NewUser(UserdetailVo userdetail) {
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
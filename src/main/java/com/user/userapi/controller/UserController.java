package com.user.userapi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.user.userapi.Entity.Userdetail;
import com.user.userapi.responce.BaseClass;
import com.user.userapi.responce.MessageStore;
import com.user.userapi.responce.ResponseVO;
import com.user.userapi.service.UserService;

@RestController

public class UserController extends BaseClass {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseVO NewUser(@RequestBody Userdetail userdetail) throws Exception {
		final ResponseVO responseVO = new ResponseVO();
		return super.success(responseVO, userService.NewUser(userdetail), MessageStore.CREATE);
	}

	@RequestMapping(value = "/userdetail", method = RequestMethod.GET)
	public ResponseVO getUser(Principal principal) {
		final ResponseVO responseVO = new ResponseVO();	
		return super.success(responseVO, userService.getUser(principal.getName()), MessageStore.GET);

	}
}
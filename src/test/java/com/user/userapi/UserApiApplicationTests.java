package com.user.userapi;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.user.userapi.Entity.Userdetail;
import com.user.userapi.repo.UserdetailRepo;
import com.user.userapi.responce.ResponseVO;
import com.user.userapi.service.UserService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
//@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserApiApplicationTests {
	
	@Autowired
    MockMvc mockMvc;
	
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    UserService userService;
    
    @MockBean
    UserdetailRepo userdetailRepo;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Before
    public void setup() {
    	
    	mockMvc=MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
	@Test
	public void testNewUser() throws Exception {
		
		Userdetail userdetail =new Userdetail();
		userdetail.setAvatar("test");
		userdetail.setEmail("test");
		userdetail.setFirst_name("test");
		userdetail.setId(1);
		userdetail.setLast_name("test");
		userdetail.setPassword("test");
		Mockito.when(userdetailRepo.save(userdetail)).thenReturn(userdetail);
		Gson gson =new Gson();
		String json =gson.toJson(userdetail);
		MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
				.contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(json)).andReturn();
		ResponseVO userdetail2 = mapper.readValue(resultActions.getResponse().getContentAsString(),ResponseVO.class);
		JsonNode json1 =mapper.readTree(mapper.writeValueAsString(userdetail2).toString());
//		String str =json1.get("email").asText();
//		System.out.println(str);
		assertNotNull(resultActions.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testGetUser() {
		
	}
}

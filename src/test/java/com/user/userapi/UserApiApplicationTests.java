package com.user.userapi;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.userapi.Entity.Userdetail;
import com.user.userapi.config.JwtRequest;
import com.user.userapi.repo.UserdetailRepo;
import com.user.userapi.responce.ResponseVO;
import com.user.userapi.service.UserService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UserApiApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@InjectMocks
	UserService userService;

	@Mock
	UserdetailRepo userdetailRepo;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	public void setup() {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Transactional
	public void testNewUser() throws Exception {

		Userdetail userdetail = new Userdetail();
		userdetail.setId(1);
		userdetail.setAvatar("test");
		userdetail.setEmail("test@gmail.com");
		userdetail.setFirst_name("test");
		userdetail.setLast_name("test");
		userdetail.setPassword("test");
		MvcResult resultActions = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userdetail)))
				.andExpect(status().isOk()).andReturn();
		assertNotNull(resultActions.getResponse().getContentAsString());

	}

	@Test
	public void testGetUser() throws Exception {

		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setEmail("emma.wong@reqres.in");
		jwtRequest.setPassword("password");
		MvcResult resultActions = mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(jwtRequest)))
				.andReturn();

		ResponseVO userdetail2 = mapper.readValue(resultActions.getResponse().getContentAsString(), ResponseVO.class);
		JsonNode json1 = mapper.readTree(mapper.writeValueAsString(userdetail2.getResponse()));
		String token = json1.get("jwttoken").asText();
		String chunks = token.split("\\.")[1];
		Base64.Decoder decoder = Base64.getDecoder();
		String payload = new String(decoder.decode(chunks));
		JsonNode json = mapper.readTree(payload);
		String name = json.get("sub").asText();
		mockMvc.perform(get("/userdetail?email=" + name).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

	}

	// test password correct or not
	@Transactional
	@Test
	public void testPassword() throws Exception {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setEmail("emma.wong@reqres.in");
		jwtRequest.setPassword("qwer");
		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(jwtRequest)))
				.andExpect(status().isBadRequest());

	}

	// test email Exist or not
	@Transactional
	@Test
	public void testEmail() throws Exception {
		Userdetail userdetail = new Userdetail();
		userdetail.setId(1);
		userdetail.setAvatar("test");
		userdetail.setEmail("emma.wong@reqres.in");
		userdetail.setFirst_name("test");
		userdetail.setLast_name("test");
		userdetail.setPassword("test");
		Mockito.when(userdetailRepo.findByEmail(userdetail.getEmail()));
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userdetail))).andExpect(status().isBadRequest());

	}

	// test username exist or not
	@Test
	public void testUsername() throws Exception {
		Userdetail userdetail = new Userdetail();
		userdetail.setId(1);
		userdetail.setAvatar("test");
		userdetail.setEmail("emma.wong@reqres.in");
		userdetail.setFirst_name("test");
		userdetail.setLast_name("test");
		userdetail.setPassword("test");
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userdetail))).andExpect(status().isBadRequest());
	}

	// test email is valid or not
	@Test
	public void testValidEmail() throws Exception {
		Userdetail userdetail = new Userdetail();
		userdetail.setId(1);
		userdetail.setAvatar("test");
		userdetail.setEmail("rameshgmail.com");
		userdetail.setFirst_name("test");
		userdetail.setLast_name("test");
		userdetail.setPassword("test");
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userdetail))).andExpect(status().isBadRequest());
	}

}

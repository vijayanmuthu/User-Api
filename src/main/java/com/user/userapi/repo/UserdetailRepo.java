package com.user.userapi.repo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.user.userapi.Entity.Userdetail;

public interface UserdetailRepo extends JpaRepository<Userdetail, Integer> {

	@Modifying
	//@Query(value = "UPDATE Userdetail u set u.value =VALUE(:updateUser) where u.email in (:updateUser)")
	
	
	List<Userdetail> findByEmailIn(Set<String> emails);

	Userdetail findByEmail(String email);
	
}

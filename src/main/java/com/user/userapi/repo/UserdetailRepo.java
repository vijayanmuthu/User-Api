package com.user.userapi.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.user.userapi.Entity.Userdetail;

public interface UserdetailRepo extends JpaRepository<Userdetail, Integer> {

	@Modifying
	@Query(value = "UPDATE Userdetail u set u.first_name = :firstname, u.last_name = :lastname,u.avatar= :avatar where u.email = :email")
	void setUserInfoByEmail(String firstname, String lastname,String avatar, String email);
	
	List<Userdetail> findByEmailIn(Set<String> emails);

	Userdetail findByEmail(String email);
	
}

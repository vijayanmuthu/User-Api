package com.user.userapi.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.userapi.Entity.Userdetail;

@Repository
public interface UserdetailRepo extends JpaRepository<Userdetail, Integer> {
	
	List<Userdetail> findByEmailIn(Set<String> keyset);

	Userdetail findByEmail(String email);
	


	
}

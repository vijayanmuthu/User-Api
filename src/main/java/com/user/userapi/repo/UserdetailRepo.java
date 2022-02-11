package com.user.userapi.repo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.userapi.Entity.Userdetail;

public interface UserdetailRepo extends JpaRepository<Userdetail, Integer> {

	List<Userdetail> findByEmailIn(Set<String> emails);

	Userdetail findByEmail(String email);
}

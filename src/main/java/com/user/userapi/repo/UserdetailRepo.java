package com.user.userapi.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.userapi.Entity.Userdetail;

@Repository
public interface UserdetailRepo extends JpaRepository<Userdetail, Integer> {
	@Query(value = "select email from userdetail where email in (:email)", nativeQuery = true)
	List<Object> findEmail(@Param("email")Set<String> email);

	Userdetail findByEmail(String email);
}

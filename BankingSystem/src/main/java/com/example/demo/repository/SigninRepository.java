package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.SigninEntity;

@Repository
public interface SigninRepository extends JpaRepository<SigninEntity,Long> {
	 Optional<SigninEntity>findByEmail(String email);
	Optional<SigninEntity> findByMobile(String mobile);
	

}

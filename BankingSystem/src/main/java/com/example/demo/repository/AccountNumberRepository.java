package com.example.demo.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AccountNumberEntity;

public interface AccountNumberRepository extends JpaRepository<AccountNumberEntity,Long>{
	Optional<AccountNumberEntity> findBySecuritypin(Long securitypin);
	Optional<AccountNumberEntity> findByAccountnumber(Long accountnumber);
	Optional<AccountNumberEntity> findByMobile(String mobile);
}

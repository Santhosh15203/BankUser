package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AdminEntity;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Long>{
	Optional<AdminEntity> findByAdminnameAndAdminpassword(String adminname,String adminpassword);
}

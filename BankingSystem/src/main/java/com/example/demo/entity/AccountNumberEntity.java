package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="AccountNumberDetails")
public class AccountNumberEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long accountnumber;
	
	private String mobile;
	private Long amount;
	private Long securitypin;
	public AccountNumberEntity() {}
	public Long getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(Long accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getSecuritypin() {
		return securitypin;
	}
	public void setSecuritypin(Long securitypin) {
		this.securitypin = securitypin;
	}
	
	
	

}

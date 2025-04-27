package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AccountNumberEntity;
import com.example.demo.entity.SigninEntity;
import com.example.demo.repository.AccountNumberRepository;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.SigninRepository;

@Service
public class MainService {
	@Autowired
	private SigninRepository signinRepo;
	
	@Autowired
	private AccountNumberRepository accnumRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	public boolean signinfun(SigninEntity entity) {
		if(signinRepo.findByMobile(entity.getMobile()).isPresent() || signinRepo.findByEmail(entity.getEmail()).isPresent()) {
			return false;
		}																									 																					 
		else {
			signinRepo.save(entity);
			return true;
		}																																						 
		
	}
	public boolean checkemailuser(String email) {
		if(signinRepo.findByEmail(email).isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean chechadminuserdetails(String adminname,String adminpassword) {
		return adminRepo.findByAdminnameAndAdminpassword(adminname,adminpassword).isPresent();
	
	
	}
	
	public SigninEntity givenloginCheck(String email,String password) {
		SigninEntity user=signinRepo.findByEmail(email).orElse(null);
		if( user!=null && user.getPassword().equals(password)) {
			return user;																																																											 
		}
		else {
			return null;
		}
	}
	
 	public SigninEntity findmobilenumberuser(String mobile) {
 		 return signinRepo.findByMobile(mobile).orElse(null);
	}
 
 	
 	public AccountNumberEntity findmobilenumberAccountRepo(String mobile) {
 		return accnumRepo.findByMobile(mobile).orElse(null);
 	}
 	
 	public boolean findmobilenumber(String mobile) {
 		return signinRepo.findByMobile(mobile).isPresent();
 	}
 	
 	
	public void accountnumberstore(AccountNumberEntity accnumEntity) {
		 accnumRepo.save(accnumEntity); 
	}
	
	
	public boolean checksecuritypin(Long securitypin) {
		return accnumRepo.findBySecuritypin(securitypin).isPresent();
		
	}
	public boolean checkaccountnumber(Long accountnumber) {
		return accnumRepo.findByAccountnumber(accountnumber).isPresent();
	}
	public AccountNumberEntity checksendersecuritypin(Long securitypin) {
		return accnumRepo.findBySecuritypin(securitypin).orElse(null);
	}
	
	
	public boolean checkaldreadyaccountholder(String mobile) {
		return accnumRepo.findByMobile(mobile).isPresent();
		}
	public void storecurrentdebitamountRepo(String mobile,Long totaldebitamount)
	{
		AccountNumberEntity user=accnumRepo.findByMobile(mobile).orElse(null);
		if(user!=null) {
			user.setAmount(totaldebitamount);
			accnumRepo.save(user);
		}
	}
	public void storecurrentcreditamountRepo(String mobile,Long totalcreditamount)
	{
		AccountNumberEntity user=accnumRepo.findByMobile(mobile).orElse(null);
		if(user!=null) {
			user.setAmount(totalcreditamount);
			accnumRepo.save(user);
		}
	}
	
	public AccountNumberEntity getreceiverbalanceamount(Long receiveraccountnumber) {
		return accnumRepo.findByAccountnumber(receiveraccountnumber).orElse(null);
	}
	
	public void addreceiveramount(Long receiverNewBalance,Long receiveraccountnumber) {
		AccountNumberEntity user=accnumRepo.findByAccountnumber(receiveraccountnumber).orElse(null);
		if(user!=null) {
			user.setAmount(receiverNewBalance);
			accnumRepo.save(user);	
		}
	}
	
	public void minussenderamount(Long senderNewBalance,Long securitypin) {
		AccountNumberEntity user=accnumRepo.findBySecuritypin(securitypin).orElse(null);
		if(user!=null) {
			user.setAmount(senderNewBalance);
			accnumRepo.save(user);
		}
	}
	public List<SigninEntity> getAllUserDetails() {
		return signinRepo.findAll();	
		}
	public List<AccountNumberEntity> getAlluserAccountDetails(){
		return accnumRepo.findAll();
	}
	
	
}

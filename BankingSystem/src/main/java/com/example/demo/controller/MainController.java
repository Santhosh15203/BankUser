package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.AccountNumberEntity;
import com.example.demo.entity.SigninEntity;
import com.example.demo.service.MainService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;


@Controller
public class MainController {
	@Autowired
	private MainService service;
	
	
	
	@GetMapping("/")
	public String startpage() {
		return "index";
	}
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/adminlogin")
	public String adminlogin() {
		return "adminlogin";
	}
	@PostMapping("/adminlogin")
	public String checkadminlogin(@RequestParam String adminname,@RequestParam String adminpassword,Model model) {
		if(service.chechadminuserdetails(adminname,adminpassword)) {
			List<SigninEntity> signinusers=service.getAllUserDetails();
			List<AccountNumberEntity>accountnumbrusers=service.getAlluserAccountDetails();
			
			List<Map<String,Object>> alluserdetails=new ArrayList<>();   //merge two table  -map inside list 
			
			for(SigninEntity users:signinusers) {
				for(AccountNumberEntity accountusers:accountnumbrusers) {
					if(users.getMobile().equals(accountusers.getMobile()) ){   //using each forloop to match mobile number
						Map<String,Object> map=new HashMap<>();                //using hashmap to store string with their object
						map.put("Signinusers", users);
						map.put("AccountNumberusers", accountusers);         
						alluserdetails.add(map);                               //then add map to the list (up)
						break;
					}
				}
			}
			model.addAttribute("alluserdetails",alluserdetails);
			return "adminview";
			
		}
		else {
			model.addAttribute("adminerror","Incorrect ! username or password.");
			return "adminlogin";	
		}
		
	}
	
	@GetMapping("/usersignin")
	public String usersignin() {
		return "usersignin";
	}
	
	@PostMapping("/signin")
	public String store(SigninEntity signin,@RequestParam String mobile,@RequestParam String email,Model model) {
		if(service.checkemailuser(email)) {
			model.addAttribute("mail", "E-mail already exists !");
			return "usersignin";
		}
		else if(service.findmobilenumber(mobile)){
			model.addAttribute("mobile", "Mobile number already exists !");
			return "usersignin";
		}
		else if(service.signinfun(signin) ){
			model.addAttribute("signinalert", "functioncall");
			return "userlogin";
		}
		else {
			model.addAttribute("signinalert", "functioncall");
			return "userlogin";
		}
		
	}

	@GetMapping("/userlogin")
	public String start2() {
		return "userlogin";
	}

	
	@PostMapping("/login")
	public String loginCheck( @RequestParam String email,@RequestParam String password,HttpSession session,Model model) {
		SigninEntity user=service.givenloginCheck(email,password);
		if(user!=null) {
			String usermobile=user.getMobile();
			session.setAttribute("name", user.getFullname());
			session.setAttribute("mobile", user.getMobile());
			session.setAttribute("email", user.getEmail());
			AccountNumberEntity useraccount=service.findmobilenumberAccountRepo(usermobile);
			if(useraccount!=null) {
				session.setAttribute("accountnumber", useraccount.getAccountnumber());
				session.setAttribute("amount", useraccount.getAmount());
				String fullname=(String)session.getAttribute("name");
				model.addAttribute("name",fullname);
				model.addAttribute("loginalert", "functioncall");
				return "main";
			}
			else {
				String fullname=(String)session.getAttribute("name");
				model.addAttribute("name",fullname);
				model.addAttribute("loginalert", "functiondontcall");
				return "main";
			}
			
		}
		else {
		     model.addAttribute("emailpassword", "enter valid email or password !");
		     model.addAttribute("signinalert", "functiondontcall");
			return "userlogin";
		}																																 
		
	}
	
	@PostMapping("/mobile")
	public String mobile(@RequestParam String mobile, Model model, HttpSession session) {
	    SigninEntity user = service.findmobilenumberuser(mobile);

	    if (user != null) {
	        session.setAttribute("name", user.getFullname());
	        session.setAttribute("mobile", user.getMobile());
	        session.setAttribute("email", user.getEmail());
	        AccountNumberEntity useraccount = service.findmobilenumberAccountRepo(mobile);

	        if (useraccount != null) {
	            session.setAttribute("accountnumber", useraccount.getAccountnumber());
	            session.setAttribute("amount", useraccount.getAmount());
	        }

	        model.addAttribute("name", user.getFullname());
	        model.addAttribute("loginalert", "functioncall");
	        return "main";

	    } else {
	        model.addAttribute("phone", "Enter valid mobile number!");
	        model.addAttribute("signinalert", "functiondontcall");
	        return "userlogin";
	    }
	}

	
	@PostMapping("/generateaccountnumber")
	public String details(AccountNumberEntity accnumEntity,@RequestParam String mobile,Model model,HttpSession session) {
		if(service.findmobilenumber(mobile)) {
			service.accountnumberstore(accnumEntity);
			AccountNumberEntity user=service.findmobilenumberAccountRepo(mobile);
			 session.setAttribute("amount", user.getAmount());
		     session.setAttribute("accountnumber", user.getAccountnumber());
		     session.setAttribute("mobile", user.getMobile());
		     Long accountnumber=(Long)session.getAttribute("accountnumber");
		     model.addAttribute("accountnumber",accountnumber);
			return "transactionmethod";
		}
		else {
			model.addAttribute("phone", "Enter valid mobile number ");
			return "accountnumber";
		}
		
	}
	
	@GetMapping("/feedback")
	public String feedbackclose() {
		return "feedback";
	}
	
	@PostMapping("/balance")
	public String bankbalance(){
		return "transactionmethod";
	}
	
	@GetMapping("/debit")
	public String debit(HttpSession session, Model model) {
	    Long amount = (Long) session.getAttribute("amount");
	    model.addAttribute("balanceamount", amount);
	    return "debit";
	}

	@GetMapping("/credit")
	public String credit(HttpSession session, Model model) {
	    Long amount = (Long) session.getAttribute("amount");
	    model.addAttribute("balanceamount", amount);
		return "credit";
	}
	@GetMapping("/transfer")
	public String transfer(HttpSession session, Model model) {
	    Long amount = (Long) session.getAttribute("amount");
	    model.addAttribute("balanceamount", amount);
		return "transfer";
	}
	@GetMapping("/balance")
	public String balance(HttpSession session, Model model) {
	    Long amount = (Long) session.getAttribute("amount");
		Long accountnumber=(Long)session.getAttribute("accountnumber");
	    model.addAttribute("balanceamount", amount);
	    model.addAttribute("accountnumber", accountnumber);
		return "balance";
	}
	@GetMapping("/main")
	public String showMainPage(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("name");
	    Long accountnumber=(Long) session.getAttribute("accountnumber");
	    model.addAttribute("name", username);
	    model.addAttribute("accountnumber", accountnumber);
	    
	    return "main";
	}

	
	@GetMapping("/transactionmethod")
	public String check(HttpSession session,Model model) {
		String mobile=(String)session.getAttribute("mobile");
		String fullname = (String)session.getAttribute("name");
		Long useraccountnumber=(Long)session.getAttribute("accountnumber");
		if(service.checkaldreadyaccountholder(mobile)) {
			model.addAttribute("accountnumber", useraccountnumber);
			return "transactionmethod";
		}
		else {
			model.addAttribute("name", fullname);
			model.addAttribute("noaccount", "you doesn't have account, creat new account !");
			model.addAttribute("loginalert", "functiondontcall");
			return "main";
		}
	}
	
	@GetMapping("/accountnumber")
	public String accountnumber(HttpSession session,Model model) {
		String mobile=(String)session.getAttribute("mobile");
		Long accountnumber=(Long)session.getAttribute("accountnumber");
		if(service.checkaldreadyaccountholder(mobile)) {
			String fullname = (String) session.getAttribute("name");
		    model.addAttribute("name", fullname);
			model.addAttribute("noaccount", "User already have account !");
			model.addAttribute("loginalert", "functiondontcall");
			return "main";
		}
		else {
			model.addAttribute("accountnumber",accountnumber);
			return "accountnumber";
		}
		
	}
	
	
	@PostMapping("/debit")
	public String debit(@RequestParam Long securitypin,@RequestParam Long debitamount,HttpSession session,Model model) {
		 Long amount = (Long) session.getAttribute("amount");
		 String mobile=(String)session.getAttribute("mobile");
		if(service.checksecuritypin(securitypin) && debitamount<=amount ) {
			Long totaldebitamount=amount-debitamount;
			session.setAttribute("amount", totaldebitamount);
			service.storecurrentdebitamountRepo(mobile,totaldebitamount);
			Long accountnumber=(Long)session.getAttribute("accountnumber");
	        model.addAttribute("balanceamount",totaldebitamount);
	        model.addAttribute("accountnumber", accountnumber);
	        model.addAttribute("amountalert", debitamount);
	        model.addAttribute("transactionalert", "debitmessage");
	        return "balance";
		}
		else {
			 model.addAttribute("balanceamount", amount);
			model.addAttribute("amountpin", "Enter valid debitamount or pin ! ");
			return "debit";
		}
	}
	
	@PostMapping("/credit")
	public String check1(@RequestParam Long securitypin,@RequestParam Long creditamount,HttpSession session,Model model) {
		Long amount=(Long)session.getAttribute("amount");
		 String mobile=(String)session.getAttribute("mobile");
		if(service.checksecuritypin(securitypin)) {
			Long totalcreditamount=amount+creditamount;
			session.setAttribute("amount",totalcreditamount);
			service.storecurrentcreditamountRepo(mobile,totalcreditamount);
			Long accountnumber=(Long)session.getAttribute("accountnumber");
		        model.addAttribute("balanceamount",totalcreditamount);
		        model.addAttribute("accountnumber", accountnumber);
		        model.addAttribute("amountalert", creditamount);
		        model.addAttribute("transactionalert", "creditmessage");
			return "balance";
		}
		else {
			model.addAttribute("balanceamount", amount);
			model.addAttribute("amountpin", "Enter valid creditamount or pin ! ");
			return "credit";
		}
	}
	
	@PostMapping("/transfer")
	public String check3(@RequestParam Long receiveraccountnumber,@RequestParam Long transferamount,@RequestParam Long securitypin,Model model,HttpSession session) {
		AccountNumberEntity receiver=service.getreceiverbalanceamount(receiveraccountnumber);
		AccountNumberEntity sender=service.checksendersecuritypin(securitypin);
		if ((receiver != null) && (sender != null) && (sender.getAmount() >= transferamount)) {
			 Long receiverNewBalance = receiver.getAmount() + transferamount;
		        Long senderNewBalance = sender.getAmount() - transferamount;
		        service.addreceiveramount(receiverNewBalance, receiveraccountnumber);
		        service.minussenderamount(senderNewBalance, securitypin);
		        session.setAttribute("amount",senderNewBalance);
		        model.addAttribute("balanceamount",senderNewBalance);
		        model.addAttribute("accountnumber", sender.getAccountnumber());
		        model.addAttribute("amountalert", transferamount);
		        model.addAttribute("transactionalert", "transfermessage");
		        return "balance";
			
		}
		else if(sender.getAmount() < transferamount){
            Long currentBalance=(Long)session.getAttribute("amount");
			model.addAttribute("balanceamount", currentBalance);
			model.addAttribute("accountpin", "Enter minimum amount from your balance amount ! ");
			return "transfer";
		}
		else {
            Long currentBalance=(Long)session.getAttribute("amount");
			model.addAttribute("balanceamount", currentBalance);
			model.addAttribute("accountpin", "Enter valid account number or security pin ! ");
			return "transfer";
		}
	}
	

}

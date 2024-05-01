package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;


@RestController
@RequestMapping("/accounts")
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	
	@Autowired
	private AccountService accountservice;
	
	//@Value("${message}")
    //private String message;
	
	
	@PostMapping("/create")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		logger.info("Account successfully created");
		Account createAccount=accountservice.createAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(createAccount);	
	}
	
	
	@GetMapping("/{id}")
	public Account getAccountByAccountNumber(@PathVariable("id") Long accountNumber) {
		logger.info("account found");
		Account account = accountservice.getAccountDetailsByAccountNumber(accountNumber);
				return account;
	}
	
	
	@GetMapping("/getallaccounts")
	public List<Account> getAllAccountDetails(){
	 List<Account> accountDetails=accountservice.getAllaccountDetails();
	return accountDetails;
	}

	@PutMapping("/deposit/{acc_number}/{amount}")
	public Account depositAccount(@PathVariable("acc_number") Long acc_number, @PathVariable("amount") Double amount) {
		logger.info("Amount deposited successfully");
	Account account=accountservice.depositAmount(acc_number, amount);
			return account;
	}
	
	@PutMapping("/withdraw/{acc_number}/{amount}")
	public Account withdrawAccount(@PathVariable("acc_number") Long acc_number, @PathVariable("amount") Double amount) {
		logger.info("withdraw completed");
	Account account=accountservice.withdrawAmount(acc_number, amount);
			return account;
	}
	
	@DeleteMapping("/delete/{acc_number}")
	public ResponseEntity<String> deleteAccount(@PathVariable("acc_number") Long acc_number) {
		logger.info("account successfully deleted");
		accountservice.closeAccount(acc_number);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("account closed");
	}
	
	
	
        
	//@GetMapping("/config")
	//public String displayMessage() {
		//return message;
		
	//}
}

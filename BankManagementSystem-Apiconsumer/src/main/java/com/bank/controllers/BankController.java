package com.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.pojo.Account;
import com.bank.service.BankService;

@RestController
@RequestMapping("/accounts")
public class BankController {
 
	@Autowired
	private BankService bankservice;
	
	@GetMapping("/retrieve/{id}")
	public ResponseEntity<Account> retrieveAccountUsingRestTemplate(@PathVariable("id") Long acc_number){
		return bankservice.retrieveAccountUsingFeignClient(acc_number);
		
	}
	@GetMapping("/retrieve/feign/{id}")
	public ResponseEntity<Account> retrieveAccountUsingFeignClient(@PathVariable("id") Long acc_number){
		return bankservice.retrieveAccountUsingFeignClient(acc_number);
		
	}
	
}

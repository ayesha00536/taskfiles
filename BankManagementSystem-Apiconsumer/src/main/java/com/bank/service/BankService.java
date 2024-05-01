package com.bank.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.pojo.Account;

public interface BankService {

	public ResponseEntity<Account> retrieveBankUsingRestTemplate(Long acc_number);
	public ResponseEntity<Account> retrieveAccountUsingFeignClient(Long acc_number);
	
}

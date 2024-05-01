package com.bank.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.pojo.Account;

@FeignClient(name = "BankManagementApp" , url = "http://localhost:8080")
public interface BankFeignClient {

	@GetMapping("/accounts/{id}")
	ResponseEntity<Account> getAccount(@PathVariable("id") Long acc_number);
}

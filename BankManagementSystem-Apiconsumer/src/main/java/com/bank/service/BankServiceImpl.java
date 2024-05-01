package com.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.bank.feignclient.BankFeignClient;
import com.bank.pojo.Account;
import com.bank.util.Constants;

@Service
public class BankServiceImpl implements BankService{

	@Autowired
	private RestTemplate resttemplate;
	
	@Autowired
	private BankFeignClient bankfeignclient;

	private static final String BANK_SERVICE_URL = "http://localhost:8080";
	@Override
	public ResponseEntity<Account> retrieveBankUsingRestTemplate(Long acc_number) {
		String url= BANK_SERVICE_URL + "/accounts/" +acc_number;
		ResponseEntity<Account> response = resttemplate.getForEntity(url, Account.class);
		return response;
	}

	@Override
	public ResponseEntity<Account> retrieveAccountUsingFeignClient(Long acc_number) {
		return bankfeignclient.getAccount(acc_number);
	}
	

}

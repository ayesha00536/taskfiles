package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Account;

public interface AccountService {

	public Account createAccount(Account account);
	public Account getAccountDetailsByAccountNumber(Long acc_number);
	public List<Account> getAllaccountDetails();
	public Account depositAmount(Long acc_number, double amount);
	public Account withdrawAmount(Long acc_number, double amount);
	public void closeAccount(Long AccountNumber);
}
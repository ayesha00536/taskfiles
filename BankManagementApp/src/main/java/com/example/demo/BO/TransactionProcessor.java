package com.example.demo.BO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;

@Service
public class TransactionProcessor {
   
	Logger logger = LoggerFactory.getLogger(TransactionProcessor.class);
	
	public Account withdrawAmmount(Account account , double amount) {
		
		if(account.getAcc_balance()<500) {
			amount+=5;//deduct amount of 5rs for each withdrawal if min bal less than 5000
			logger.info("Rs 5 is deducted as transaction fee as available balance is less than 500");
		}		
		account.setAcc_balance(account.getAcc_balance()-amount);
		return account;
		
	}
}

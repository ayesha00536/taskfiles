package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.BO.TransactionProcessor;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{

	Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountRepository accountrepository;
	
	@Autowired
	private TransactionProcessor processor;
	
	@Override
	public Account createAccount(Account account) {
		logger.info("Account creation successfull");
		Account saveAccount= accountrepository.save(account);
		return saveAccount;
	}
	
	
	
	@Override
	public Account getAccountDetailsByAccountNumber(Long acc_number) {
		logger.info("found account details");
		Optional<Account> account=accountrepository.findById(acc_number);
		if(account.isEmpty()) {
			throw new RuntimeException("no account");
		}
		Account account_found= account.get();
		return account_found;
	}

	@Override
	public List<Account> getAllaccountDetails() {
		List<Account> listOfAccount=accountrepository.findAll();
		return listOfAccount;
	}

	@Override
	public Account depositAmount(Long acc_number, double amount) {
		logger.info("amount deposited");
		Optional<Account>account=accountrepository.findById(acc_number);
		if(account.isEmpty()) {
			throw new RuntimeException("account not present");
		}
		Account accountpresent= account.get();
		Double totalBalance=accountpresent.getAcc_balance()+amount;
		accountpresent.setAcc_balance(totalBalance);
		accountrepository.save(accountpresent);
		return accountpresent;
	}

	@Override
	public Account withdrawAmount(Long acc_number, double amount) {
		logger.info("amount debited");
		Optional<Account> account=accountrepository.findById(acc_number);
		if(account.isEmpty()) {
			throw new RuntimeException("no account");
		}
		Account account_found= account.get();
		Account accountAfterWithdraw = processor.withdrawAmmount(account_found, amount);//transaction fee is added if min bal is less than 500
		accountrepository.save(accountAfterWithdraw);
		return accountAfterWithdraw;
		
	}
	

	@Override
	public void closeAccount(Long AccountNumber) {
		logger.info("account deleted");
		getAccountDetailsByAccountNumber(AccountNumber);
		accountrepository.deleteById(AccountNumber);
		
	}

}

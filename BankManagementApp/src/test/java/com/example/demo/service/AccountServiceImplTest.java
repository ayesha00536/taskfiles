package com.example.demo.service;


import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.demo.BO.TransactionProcessor;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

import com.example.demo.service.AccountServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.client.WireMock;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class, WireMockExtension.class, MockitoExtension.class })
public class AccountServiceImplTest {
      
	 private static final Logger logger = LoggerFactory.getLogger(AccountServiceImplTest.class);

	
	@Autowired
    private AccountServiceImpl accountServiceimpl;
	
	@LocalServerPort
	private int port;
	
	 @Mock
	 private TransactionProcessor transactionProcessor;
	
	@Mock
    private AccountRepository accountRepository;

	private WireMockServer wireMockServer;
	
	
	
	
	
	@BeforeEach
	public void setUp() {
        wireMockServer = new WireMockServer(8081);// creating instance of wiremock server
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port()); // wiremock will handled the http request made by the system usinh port 8081 running on localhost
    }
		
	
		
	@Test
	public void testCreateAccount() {
		stubFor(post(urlEqualTo("/accounts/save"))
				.willReturn((aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"acc_number\": \"786\", \"acc_holder_name\": \"Ayesha\"}"))));
		
		AccountService accountService = new AccountServiceImpl();
		
		Account account = new Account();
		account.setAcc_holder_name("Ayesha");
		
		Account createAccount = accountServiceimpl.createAccount(account);
		
		assertEquals("786",createAccount.getAcc_number());
		assertEquals("Ayesha", createAccount.getAcc_holder_name());
	}
	
	

	@Test
	public void testGetAccountDetailsByAccountNumber() {
		long acc_number=786;
		stubFor(get(urlEqualTo("/accounts/findById/acc_number" ))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"acc_number\": \"786\": \"acc_holder_name\": \"Ayesha\"}")));
		
		
	    Account account = accountServiceimpl.getAccountDetailsByAccountNumber(acc_number);
	    
	    assertEquals("786", account.getAcc_number());
	    assertEquals("Ayesha", account.getAcc_holder_name());
	}
	
	@Test
	public void testGetAllAccountDetails() {
		Account account1 = new Account(898, "Aasif",88000);
		Account account2 = new Account(887, "Aamir",400);
		
		List<Account> sampleAccount = Arrays.asList(account1, account2);
		
		when(accountRepository.findAll()).thenReturn(sampleAccount);
		
		List<Account> allAccount = accountServiceimpl.getAllaccountDetails();
		
		assertEquals(sampleAccount.size(), allAccount.size());
		assertEquals(sampleAccount.get(1), allAccount.get(1));
		assertEquals(sampleAccount.get(2), allAccount.get(2));
	}
	
	@Test
	public void testDepositAmount() {
		Long acc_number = 786L;
		double depositAmount = 100.0;
		double initialbalance = 500.0;
		
		Account account = new Account(acc_number, "Ayesha", initialbalance);
		
		when(accountRepository.findById(acc_number)).thenReturn(Optional.of(account));
		Account depositedAccount = accountServiceimpl.depositAmount(acc_number, depositAmount);
		
		assertEquals(initialbalance + depositAmount, depositedAccount.getAcc_balance());			
				
	}

	

	@Test
	public void testWithdrawAmount() {
		 Long acc_number = 786L;
	        double withdrawAmount = 100.0;
	        double initialBalance = 500.0;
	        Account account = new Account(acc_number, "Ayesha", initialBalance);
	        
	        when(accountRepository.findById(acc_number)).thenReturn(Optional.of(account));
	        when(transactionProcessor.withdrawAmmount(eq(account), eq(withdrawAmount))).thenReturn(account);
	        
	        Account withdrawnAccount = accountServiceimpl.withdrawAmount(acc_number, withdrawAmount);

	        assertEquals(account, withdrawnAccount);
	        verify(accountRepository, times(1)).save(account);
	        
	        
	}
	        @Test
	        
	        public void testWithdrawAmountAccountNotPresent() {
	        	
	        	when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
	        	
	        	RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	        	
	        	accountServiceimpl.withdrawAmount(798L, 100.0);
	        });
	        }
	        
	        
	   @Test
	   public void testCloseAccount() {
		   stubFor(post(urlEqualTo("/log"))
				   .willReturn(aResponse()
						   .withStatus(200)));
		   
		   stubFor(get(urlEqualTo("/account/" +887L))
				   .willReturn(aResponse()
						   .withStatus(200)
						   .withBody("{acc_number\": 887L, \"acc_holder_name\": \"Aamir\"}")));
		   
		   stubFor(delete(urlEqualTo("/account/" +887L))
				   .willReturn(aResponse()
						   .withStatus(200)));
		   
		   AccountServiceImpl accountserviceimpl = new AccountServiceImpl();
		   accountserviceimpl.closeAccount(887L);
		   
		   verify(postRequestedFor(urlEqualTo("/log"))
				   .withRequestBody(equalToJson("{\"message\": \"account deleted\"}")));
		   
		   verify(getRequestedFor(urlEqualTo("/account/" + 887L)));
		   
		   verify(deleteRequestedFor(urlEqualTo("/account/" + 887L)));
		   	
	        
	}
	
	
	@AfterEach
    public void stopWireMockServer() {
		wireMockServer.stop();
	}
	
}

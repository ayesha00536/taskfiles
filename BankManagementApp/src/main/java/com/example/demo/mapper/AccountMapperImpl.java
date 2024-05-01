package com.example.demo.mapper;

import com.example.demo.DTO.AccountDTO;
import com.example.demo.entity.Account;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-13T16:02:29+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.4 (Eclipse Adoptium)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDTO entityToDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        return accountDTO;
    }

    @Override
    public Account dtoToAccount(AccountDTO accountdto) {
        if ( accountdto == null ) {
            return null;
        }

        Account account = new Account();

        return account;
    }
}

package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.DTO.AccountDTO;
import com.example.demo.entity.Account;

@Mapper
public interface AccountMapper {
	
	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
	
	AccountDTO entityToDTO(Account account);
	Account dtoToAccount(AccountDTO accountdto);

}

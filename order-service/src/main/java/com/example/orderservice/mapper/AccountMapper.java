package com.example.orderservice.mapper;

import com.example.orderservice.dto.AccountDTO;
import com.example.orderservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account toAccount(AccountDTO accountDTO);

    AccountDTO toAccountDTO(Account account);
}

package com.example.orderservice.service;

import com.example.orderservice.dto.AccountDTO;

public interface AccountService {

    AccountDTO findAccountByAccountId(Integer accountId);

    Integer insertAccount(AccountDTO accountDTO);

    Integer updateAccount(AccountDTO accountDTO);

}

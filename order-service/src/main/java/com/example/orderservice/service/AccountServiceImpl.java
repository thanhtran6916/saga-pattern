package com.example.orderservice.service;

import com.example.orderservice.dto.AccountDTO;
import com.example.orderservice.entity.Account;
import com.example.orderservice.mapper.AccountMapper;
import com.example.orderservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    @Override
    public AccountDTO findAccountByAccountId(Integer accountId) {
        AccountDTO accountDTO = accountMapper.toAccountDTO(accountRepository
                .findAccountByAccountId(accountId));
        if (ObjectUtils.isEmpty(accountDTO)) return null;
        return accountDTO;
    }

    @Override
    public Integer insertAccount(AccountDTO accountDTO) {
        Account account = accountRepository.save(accountMapper.toAccount(accountDTO));
        if (ObjectUtils.isEmpty(account)) return null;
        return account.getId();
    }

    @Override
    public Integer updateAccount(AccountDTO accountDTO) {
        Account account = accountRepository.findAccountByAccountId(accountDTO.getAccountId());
        if (ObjectUtils.isEmpty(account)) return null;

        accountDTO.setId(account.getId());
        accountDTO.setCreatedDate(account.getCreatedDate());

        account = accountRepository.save(accountMapper.toAccount(accountDTO));
        if (ObjectUtils.isEmpty(account)) return null;
        return account.getAccountId();
    }
}

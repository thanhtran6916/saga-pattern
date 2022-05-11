package com.example.loansservice.service;

import com.example.loansservice.dto.LoansProfileDTO;

public interface LoansProfileService {

    LoansProfileDTO findByAccountId(Integer accountId);

    Integer updateLoansProfile(LoansProfileDTO loansProfileDTO);

    Integer insertLoansProfile(LoansProfileDTO loansProfileDTO);

}

package com.example.loansservice.repository;

import com.example.loansservice.entity.LoansProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansProfileRepository extends JpaRepository<LoansProfile, Integer> {

    LoansProfile findLoansProfileByAccountId(Integer accountId);
}

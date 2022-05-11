package com.example.loansservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoansProfileDTO {

    private Integer id;

    private Integer accountId;

    private String bankAccountId;

    private String bankName;

    private Date createdDate;
}

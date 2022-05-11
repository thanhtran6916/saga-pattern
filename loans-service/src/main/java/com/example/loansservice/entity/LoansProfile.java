package com.example.loansservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "LOANS_PROFILE")
public class LoansProfile extends BaseEntity {

    @Id
    @GeneratedValue(generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "loans_profile_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ACCOUNT_ID")
    private Integer accountId;

    @Column(name = "BANK_ACCOUNT_ID")
    private String bankAccountId;

    @Column(name = "BANK_NAME")
    private String bankName;

}

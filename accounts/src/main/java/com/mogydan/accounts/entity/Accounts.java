package com.mogydan.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Accounts extends BaseEntity {
    @Id
    private Long accountNumber;

    private Long customerId;
    private String accountType;
    private String branchAddress;

    @Column(name = "communication_sw")
    private Boolean communicationSw;
}

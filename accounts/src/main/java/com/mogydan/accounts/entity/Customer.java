package com.mogydan.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GenericGenerator(name = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long customerId;

    private String name;
    private String email;
    private String mobileNumber;
}

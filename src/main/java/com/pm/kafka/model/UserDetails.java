package com.pm.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetails {

    private int userId;
    private String firstName;
    private String lastName;
    private List<String> mobileNumberList;
    private Map<String,BankAccountDetail> bankAccountDetails;
}

package com.pm.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankAccountDetail {

    String bankId;
    String bankIFSCNumber;
    String bankAccountNumber;
    String bankUPIId;
}

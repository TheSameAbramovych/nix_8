package ua.com.alevel.module_3.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String wallet;
    private BigDecimal amount;
    private String targetWalletNumber;
}

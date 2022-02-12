package ua.com.alevel.module_3.controller.dto;

import lombok.Data;
import ua.com.alevel.module_3.entity.Currency;

@Data
public class WalletRequest {
    private Currency currency;
    private Long userId;
}

package ua.com.alevel.module_3.service;

import ua.com.alevel.module_3.entity.Currency;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal getExchangeBuyRate(Currency from, Currency to);

    BigDecimal getExchangeSaleRate(Currency from, Currency to);
}

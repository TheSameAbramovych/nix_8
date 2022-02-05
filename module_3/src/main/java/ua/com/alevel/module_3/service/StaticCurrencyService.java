package ua.com.alevel.module_3.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ua.com.alevel.module_3.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import static ua.com.alevel.module_3.entity.Currency.*;

@Service
public class StaticCurrencyService implements CurrencyService {
    private static final Map<Pair<Currency, Currency>, BigDecimal> EXCHANGE_RATES = Map.of(
            Pair.of(PLN, UAH), new BigDecimal("7.06"),
            Pair.of(PLN, GBP), new BigDecimal("0.19"),
            Pair.of(PLN, EUR), new BigDecimal("0.22"),
            Pair.of(PLN, USD), new BigDecimal("0.25"),
            Pair.of(GBP, UAH), new BigDecimal("38.05"),
            Pair.of(GBP, EUR), new BigDecimal("1.18"),
            Pair.of(GBP, USD), new BigDecimal("1.35"),
            Pair.of(USD, UAH), new BigDecimal("28.12"),
            Pair.of(USD, EUR), new BigDecimal("0.87"),
            Pair.of(EUR, UAH), new BigDecimal("32.21")
    );

    @Override
    public BigDecimal getExchangeBuyRate(Currency from, Currency to) {
        return Optional.ofNullable(EXCHANGE_RATES.get(Pair.of(from, to)))
                .orElseGet(() -> getExchangeSaleRate(to, from));
    }

    @Override
    public BigDecimal getExchangeSaleRate(Currency from, Currency to) {
        return BigDecimal.ONE.divide(getExchangeBuyRate(from, to), RoundingMode.DOWN);
    }
}

package ua.com.alevel.module_3.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ua.com.alevel.module_3.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static ua.com.alevel.module_3.entity.Currency.*;

@Service
public class StaticCurrencyService implements CurrencyService {
    private static final Map<Pair<Currency, Currency>, BigDecimal> EXCHANGE_RATES = Map.of(
            Pair.of(PLN, UAH), new BigDecimal("7.0612323"),
            Pair.of(PLN, GBP), new BigDecimal("0.1912323"),
            Pair.of(PLN, EUR), new BigDecimal("0.2212323"),
            Pair.of(PLN, USD), new BigDecimal("0.2512332"),
            Pair.of(GBP, UAH), new BigDecimal("38.0523354"),
            Pair.of(GBP, EUR), new BigDecimal("1.1824442"),
            Pair.of(GBP, USD), new BigDecimal("1.3542243"),
            Pair.of(USD, UAH), new BigDecimal("28.1225334"),
            Pair.of(USD, EUR), new BigDecimal("0.8723424"),
            Pair.of(EUR, UAH), new BigDecimal("32.2125342")
    );

    @Override
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        if (Objects.equals(from, to)) {
            return BigDecimal.ONE;
        }

        return Optional.ofNullable(EXCHANGE_RATES.get(Pair.of(from, to)))
                .orElseGet(() -> BigDecimal.ONE.setScale(7, RoundingMode.HALF_DOWN)
                        .divide(EXCHANGE_RATES.get(Pair.of(to, from)), RoundingMode.HALF_DOWN));
    }
}

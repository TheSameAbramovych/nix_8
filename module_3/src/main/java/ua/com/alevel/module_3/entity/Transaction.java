package ua.com.alevel.module_3.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
public class Transaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal exchangeRate;
    private Operation operation;
    private String dependencyWallet;
    private Date processedDate;
}

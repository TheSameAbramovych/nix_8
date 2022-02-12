package ua.com.alevel.module_3.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Setter
@Entity
public class Transaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @OnDelete(action = CASCADE)
    private Wallet wallet;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal exchangeRate;
    private Operation operation;
    private String dependencyWallet;
    private Date processedDate;
}

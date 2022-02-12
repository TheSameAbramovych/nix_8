package ua.com.alevel.module_3.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Wallet {
    @Id
    private String number;
    private BigDecimal amount;
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "wallet")
    private Set<Transaction> transactions;
    private boolean close;

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}


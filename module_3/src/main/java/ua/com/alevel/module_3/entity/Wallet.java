package ua.com.alevel.module_3.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

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
    @OnDelete(action = CASCADE)
    private User user;
    @OneToMany(mappedBy = "wallet")
    @OnDelete(action = CASCADE)
    private Set<Transaction> transactions;
    private boolean close;

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}


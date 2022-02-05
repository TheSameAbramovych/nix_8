package ua.com.alevel.module_3.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "user")
    private Set<Wallet> wallets;
}

package ua.com.alevel.module_3.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.alevel.module_3.dao.WalletDao;
import ua.com.alevel.module_3.entity.Wallet;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletDao walletDao;

    @Override
    public Wallet findByNumber(String id) {
        return null;
    }
}

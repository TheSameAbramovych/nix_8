package ua.com.alevel.module_3.service;

import org.springframework.stereotype.Service;
import ua.com.alevel.module_3.controller.dto.PageAndSizeData;
import ua.com.alevel.module_3.controller.dto.SortData;
import ua.com.alevel.module_3.dao.LimitedRequest;
import ua.com.alevel.module_3.dao.UserDao;
import ua.com.alevel.module_3.dao.WalletDao;
import ua.com.alevel.module_3.entity.User;
import ua.com.alevel.module_3.entity.Wallet;
import ua.com.alevel.module_3.exception.EmptyNameException;
import ua.com.alevel.module_3.exception.IncorrectNameException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final WalletDao walletDao;

    public UserServiceImpl(UserDao userDao, WalletDao walletDao) {
        this.userDao = userDao;
        this.walletDao = walletDao;
    }

    public void create(User entity) {
        if (!entity.getLastName().matches("^[a-zA-ZА-Яа-яІіЇїЁёЫы]+$")
                || !entity.getFirstName().matches("^[a-zA-ZА-Яа-яІіЇїЁёЫы]+$")) {
            throw new IncorrectNameException();
        }

        if (entity.getFirstName().isEmpty() || entity.getLastName().isEmpty()) {
            throw new EmptyNameException();
        }
        userDao.create(entity);
    }

    public void update(User entity) {
        userDao.update(entity);
    }

    public void changeStatus(Long id) {
        User user = userDao.findById(id);
        user.setActive(!user.isActive());
        userDao.update(user);
        List<Wallet> wallets = user.getWallets().stream().toList();
        if (!user.isActive()) {
            wallets.forEach(wallet -> wallet.setClose(true));
            wallets.forEach(walletDao::update);
        }
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public List<User> findAll(PageAndSizeData pageAndSizeData, SortData sortData) {
        LimitedRequest request = new LimitedRequest();
        request.setOrder(sortData.getOrder());
        request.setSort(sortData.getSort());
        request.setPageSize(pageAndSizeData.getSize());
        request.setCurrentPage(pageAndSizeData.getPage());

        return userDao.findAll(request);
    }

    public long count() {
        return userDao.count();
    }
}

package ua.com.alevel.module_3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.module_3.controller.dto.WalletRequest;
import ua.com.alevel.module_3.entity.Wallet;
import ua.com.alevel.module_3.service.UserService;
import ua.com.alevel.module_3.service.WalletService;

@Controller
@RequestMapping("/wallet")
public class WalletController extends BaseController {
    private final WalletService walletService;
    private final UserService userService;

    public WalletController(WalletService walletService, UserService userService) {
        this.walletService = walletService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createWallet(WalletRequest walletRequest) {
        walletService.create(userService.findById(walletRequest.getUserId()), walletRequest.getCurrency());
        return "redirect:/users/details/" + walletRequest.getUserId();
    }

    @PostMapping("/disable/{number}")
    public String disableWallet(@PathVariable String number) {
        Wallet wallet = walletService.findByNumber(number);
        wallet.setClose(!wallet.isClose());
        walletService.update(wallet);
        return "redirect:/users/details/" + wallet.getUser().getId();
    }

}

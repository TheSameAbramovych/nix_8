package ua.com.alevel.module_3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.module_3.controller.dto.ReportRequest;
import ua.com.alevel.module_3.controller.dto.TransactionRequest;
import ua.com.alevel.module_3.entity.Wallet;
import ua.com.alevel.module_3.service.TransactionReportCreator;
import ua.com.alevel.module_3.service.TransactionService;
import ua.com.alevel.module_3.service.WalletService;

import java.util.Date;

@Controller
@RequestMapping("/transaction")
public class TransactionController extends BaseController {
    private final TransactionService transactionService;
    private final WalletService walletService;
    private final TransactionReportCreator reportCreatorService;

    public TransactionController(TransactionService transactionService, WalletService walletService,
                                 TransactionReportCreator reportCreatorService) {
        this.transactionService = transactionService;
        this.walletService = walletService;
        this.reportCreatorService = reportCreatorService;
    }

    @PostMapping("/transfer")
    public String transfer(TransactionRequest transaction, RedirectAttributes redirectAttributes) {
        Wallet wallet = walletService.findByNumber(transaction.getWallet());
        errorHandling(() -> transactionService.transfer(wallet, transaction.getAmount(), transaction.getTargetWalletNumber()), redirectAttributes);
        return "redirect:/users/details/" + wallet.getUser().getId();
    }

    @PostMapping(value = "/report", produces = "text/csv")
    @ResponseBody
    public byte[] generateCsv(ReportRequest request) {
        Date to = new Date();
        return reportCreatorService.createCsvReport(request.getUserId(), request.getWallet(), request.getPeriod().getFrom(to), to);
    }
}

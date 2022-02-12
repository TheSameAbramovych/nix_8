package ua.com.alevel.module_3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.module_3.controller.dto.*;
import ua.com.alevel.module_3.entity.User;
import ua.com.alevel.module_3.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String allUsers(User user, Model model, PageAndSizeData pageAndSizeData, SortData sortData) {
        pageAndSizeData = RequestHelper.getValidPageAndSizeData(pageAndSizeData);
        sortData = RequestHelper.getValidSortData(sortData);

        List<User> students = userService.findAll(pageAndSizeData, sortData);
        int size = pageAndSizeData.getSize();
        pageAndSizeData.setPageCount(userService.count() % size == 0 ? userService.count() / size : userService.count() / size + 1);

        model.addAttribute("users", students);
        model.addAttribute("pageAndSize", pageAndSizeData);
        model.addAttribute("sortData", sortData);
        model.addAttribute("user", user);

        return "pages/user/user_all";
    }

    @PostMapping("/create")
    public String createUser(User user, RedirectAttributes redirectAttributes) {
        errorHandling(() -> userService.create(user), redirectAttributes);
        return "redirect:/users";
    }

    @GetMapping("/details/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        WalletRequest wallet = new WalletRequest();
        ReportRequest reportRequest = new ReportRequest();
        wallet.setUserId(id);
        reportRequest.setUserId(id);

        model.addAttribute("wallet", wallet);
        model.addAttribute("report", reportRequest);
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("transaction", new TransactionRequest());

        return "pages/user/user_details";
    }

    @PostMapping("/status/{id}")
    public String changeStatusUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        errorHandling(() -> userService.changeStatus(id), redirectAttributes);
        return "redirect:/users";
    }
}

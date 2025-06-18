package com.beer.order_forcast.controller;

import com.beer.order_forcast.model.Account;
import com.beer.order_forcast.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (userId == null || isAdmin == null) {
            model.addAttribute("error", "ログイン情報が不正です。");
            return "error";
        }

        if (isAdmin) {
            List<Account> accounts = accountService.getAllActiveAccounts();
            model.addAttribute("accounts", accounts);
            return "accounts";  // ← ✅ 遷移先を accounts.html に変更
        } else {
            model.addAttribute("message", "一般ユーザーはアクセスできません。");
            return "error";
        }
    }
}

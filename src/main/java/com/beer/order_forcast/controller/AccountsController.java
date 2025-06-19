package com.beer.order_forcast.controller;

import com.beer.order_forcast.model.Account;
import com.beer.order_forcast.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

    //  // 編集ページ表示
    // @GetMapping("/edit/{id}")
    // public String editAccount(@PathVariable Long id, Model model) {
    //     Optional<Account> account = accountRepository.findById(id);
    //     if (account.isPresent()) {
    //         model.addAttribute("account", account.get());
    //         return "account-edit"; // account-edit.html に対応
    //     } else {
    //         return "redirect:/accounts";
    //     }
    // }

    // // 編集処理（PUT受け取り）
    // @PutMapping("/{id}")
    // @ResponseBody
    // public String updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
    //     Optional<Account> accountOpt = accountRepository.findById(id);
    //     if (accountOpt.isPresent()) {
    //         Account account = accountOpt.get();
    //         account.setUsername(updatedAccount.getUsername());
    //         account.setEmail(updatedAccount.getEmail());
    //         account.setRole(updatedAccount.getRole());
    //         accountRepository.save(account);
    //         return "OK";
    //     } else {
    //         return "NG";
    //     }
    // }


}

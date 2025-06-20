package com.beer.order_forcast.controller;

import com.beer.order_forcast.service.AccountService;
import com.beer.order_forcast.model.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        int result = accountService.login(userId, password);
        if (result == 1) {
            model.addAttribute("error", "メールアドレスが存在しません。");
            return "login";
        } else if (result == 2) {
            model.addAttribute("error", "パスワードが間違っています。");
            return "login";
        } else if (result == 0) {
            // ここで Account を取得して session に保存する（非常に重要）
            Account account = accountService.getAccountByEmail(userId);
            session.setAttribute("userId", account.getId());
            session.setAttribute("isAdmin", account.isAdmin());
            model.addAttribute("isAdmin", account.isAdmin());
            System.out.println("管理者として");
            return "redirect:/history_all";
        } else if (result == 4) {
            System.out.println("一般ユーザーとして");
            return "redirect:/sales_input";
        } else {
            model.addAttribute("error", "不明なエラーが発生しました。");
            return "login";
        }

    }
}
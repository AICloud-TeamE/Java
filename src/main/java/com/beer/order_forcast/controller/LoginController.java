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
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        return accountService.login(email, password)
                .map(account -> {
                    session.setAttribute("userId", account.getId());
                    session.setAttribute("isAdmin", account.isAdmin());
                    return "redirect:/home";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "メールアドレスまたはパスワードが違います。");
                    return "login";
                });
    }
}

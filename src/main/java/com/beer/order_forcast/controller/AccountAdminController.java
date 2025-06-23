package com.beer.order_forcast.controller;

import com.beer.order_forcast.model.Account;
import com.beer.order_forcast.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AccountAdminController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public String showAccountList(HttpSession session, Model model) {
        String name = (String) session.getAttribute("userName");
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        model.addAttribute("userName", name);
        model.addAttribute("userId", userId);
        model.addAttribute("isAdmin", isAdmin);

        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "管理者のみアクセス可能です。");
            return "error";
        }

        List<Account> accounts = accountService.getAllActiveAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @GetMapping("/accounts/add")
    public String showAddForm(Model model) {
        model.addAttribute("account", new Account());
        return "account_add";
    }

    @PostMapping("/accounts/success")
    public String addAccount(@RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            HttpSession session,
            Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "管理者のみアクセス可能です。");
            return "error";
        }

          // すでに同じメールが存在するかチェック
        // if (accountService.existsByEmail(email)) {
        //     model.addAttribute("emailError", "このメールアドレスは既に使用されています。");
        //     model.addAttribute("account", new Account()); // フォーム再表示のため
        //     return "account_add";
        // }
          boolean hasError = false;

        // メールアドレス重複チェック
        if (accountService.existsByEmail(email)) {
            model.addAttribute("emailExistsError", true);
            hasError = true;
         }

        // パスワードの長さチェック
        if (password.length() < 8) {
            model.addAttribute("passwordError", true);
            hasError = true;
         }
         
         if (hasError) {
        // 入力済み情報を保持して戻る
        Account account = new Account();
        account.setName(username);
        account.setEmail(email);
        account.setPassword(password);
        account.setAdmin("admin".equals(role));
        model.addAttribute("account", account);
        return "account_add";
         }
 
        
        // 登録処理 
<<<<<<< HEAD
=======

>>>>>>> 58f03ca4f092557532dd99f20aab3ccd31cbb953
        Account account = new Account();
        account.setName(username);
        account.setEmail(email);
        account.setPassword(password);
        account.setAdmin(role.equals("admin"));
        account.setDeleted(false);

        accountService.saveAccount(account);
        
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/delete")
    public String deleteAccount(@RequestParam("id") Integer id, HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "管理者のみアクセス可能です。");
            return "error";
        }
        accountService.softDeleteAccount(id);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/edit/{id}")
    public String showEditForm(@PathVariable Integer id, HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        System.out.println("Session ID: " + session.getId());
        System.out.println("isAdmin: " + session.getAttribute("isAdmin"));
        System.out.println("userId: " + session.getAttribute("userId"));
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "管理者のみアクセス可能です。");
            return "error";
        }

        Optional<Account> optionalAccount = accountService.findById(id);
        if (optionalAccount.isEmpty()) {
            model.addAttribute("error", "アカウントが見つかりません。");
            return "error";
        }

        model.addAttribute("account", optionalAccount.get());
        return "account_edit";
    }

    @PostMapping("/accounts/update/{id}")
    public String updateAccount(@PathVariable Integer id,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String role,
            HttpSession session,
            Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "管理者のみアクセス可能です。");
            return "error";
        }

        Optional<Account> optionalAccount = accountService.findById(id);
        if (optionalAccount.isEmpty()) {
            model.addAttribute("error", "アカウントが見つかりません。");
            return "error";
        }

        Account account = optionalAccount.get();
        account.setName(username);
        account.setEmail(email);
        account.setAdmin("管理者".equals(role));

        accountService.saveAccount(account);
        return "redirect:/accounts";
    }
}
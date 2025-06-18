package com.beer.order_forcast.controller;

import com.beer.order_forcast.model.Account;
import com.beer.order_forcast.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class AccountAdminController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/admin/accounts")
    public String showAccountList(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "管理者のみアクセス可能です。");
            return "error";
        }

        List<Account> accounts = accountService.getAllActiveAccounts();
        model.addAttribute("accounts", accounts);
        return "account-list";
    }

    @GetMapping("/accounts/add")
    public String showAddForm(Model model) {
    model.addAttribute("account", new Account());
    return "account_add"; // ← ファイル名と完全一致（拡張子除く）
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

    Account account = new Account();
    account.setName(username);
    account.setEmail(email);
    account.setPassword(password);
    account.setAdmin(role.equals("admin"));  // 管理者なら true、従業員なら false
    account.setDeleted(false);

    accountService.saveAccount(account); // アカウントを保存
    session.setAttribute("userId", account.getId());
    session.setAttribute("isAdmin", account.isAdmin());
    // return "redirect:/admin/accounts"; // アカウント一覧（accounts.html）へリダイレクト
     return "redirect:/home";
}




    @GetMapping("/admin/delete/{id}")
    public String deleteAccount(@PathVariable Integer id, HttpSession session, Model model) {
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin == null || !isAdmin) {
        model.addAttribute("error", "管理者のみアクセス可能です。");
        return "error";
    }
    accountService.softDeleteAccount(id);
    return "redirect:/admin/accounts";
}

    

}

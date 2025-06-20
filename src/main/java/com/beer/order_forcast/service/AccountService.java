package com.beer.order_forcast.service;

import com.beer.order_forcast.model.Account;
import com.beer.order_forcast.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // 取得未被删除的全部账号
    public List<Account> getAllActiveAccounts() {
        return accountRepository.findByIsDeletedFalse();
    }

    // 软删除账号
    public void softDeleteAccount(Integer id) {
        accountRepository.findById(id).ifPresent(account -> {
            account.setDeleted(true);
            accountRepository.save(account);
        });
    }

    // 保存或更新账号
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    // 登录：先通过 email 找，再比对密码
    // public Optional<Account> login(String email, String password) {
    // return accountRepository.findByEmailAndIsDeletedFalse(email)
    // .filter(account -> account.getPassword().equals(password));
    // }
    public int login(String email, String password) {
        Optional<Account> optionalAccount = accountRepository.findByEmailAndIsDeletedFalse(email);

        if (!optionalAccount.isPresent()) {
            return 1; // 用户不存在
        }

        Account account = optionalAccount.get();
        if (!account.getPassword().equals(password)) {
            return 2; // 密码错误
        }

        return 0; // 登录成功
    }

    // 登录成功时取出用户详细信息用
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmailAndIsDeletedFalse(email).orElse(null);
    }

    // 通过 ID 查找账号
    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    public boolean existsByEmail(String email) {
        return accountRepository.findByEmailAndIsDeletedFalse(email).isPresent();
}

}

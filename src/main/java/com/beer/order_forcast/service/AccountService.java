package com.beer.order_forcast.service;

import com.beer.order_forcast.model.Account;
import java.util.List;
import java.util.Optional;

// public interface AccountService {
//     List<Account> getAllActiveAccounts();
//     void softDeleteAccount(Integer id);
//     void saveAccount(Account account);

//      // ✅ 加上这个方法定义
//     Optional<Account> login(String email, String password);
//     Account findById(Integer id); // ← アカウント ID で取得

//     Optional<Account> findById(Integer id);

// }

public interface AccountService {
    List<Account> getAllActiveAccounts();
    void softDeleteAccount(Integer id);
    void saveAccount(Account account);
    Optional<Account> login(String email, String password);

    // ✅ これだけ残す！
    Optional<Account> findById(Integer id);
}



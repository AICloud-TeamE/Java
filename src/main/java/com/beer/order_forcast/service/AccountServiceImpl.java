// package com.beer.order_forcast.service;

// import com.beer.order_forcast.model.Account;
// import com.beer.order_forcast.repository.AccountRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class AccountServiceImpl implements AccountService {

//     @Autowired
//     private AccountRepository accountRepository;

//     @Override
//     public List<Account> getAllActiveAccounts() {
//         return accountRepository.findByIsDeletedFalse();
//     }

//     @Override
//     public void softDeleteAccount(Integer id) {
//         Account account = accountRepository.findById(id).orElse(null);
//         if (account != null) {
//             account.setDeleted(true);
//             accountRepository.save(account);
//         }
//     }

//     // ✅ 這就是你需要的 saveAccount 方法
//     @Override
//     public void saveAccount(Account account) {
//         accountRepository.save(account);
//     }

 


//     // @Override
//     // public Optional<Account> login(String email, String password){
//     //   Optional<Account> aa = Optional.empty();
//     //   return aa;  
//     // }

//     @Override
//     public Optional<Account> login(String email, String password){
//         return accountRepository.findByEmailAndIsDeletedFalse(email)
//             .filter(account -> account.getPassword().equals(password));
//     }

//     // @Override
//     // public Account findById(Integer id) {
//     //     return accountRepository.findById(id).orElse(null);
//     // }

//     @Override
//     public Optional<Account> findById(Integer id) {
//     return accountRepository.findById(id);
//     }


// }
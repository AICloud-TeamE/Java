package com.beer.order_forcast.repository;

import com.beer.order_forcast.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmailAndIsDeletedFalse(String email);

    List<Account> findByIsDeletedFalse(); // 追加
}




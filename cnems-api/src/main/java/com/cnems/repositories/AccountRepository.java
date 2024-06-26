package com.cnems.repositories;

import com.cnems.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository  extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);

    @Query(value = "SELECT SUM(balance) FROM public.account WHERE user_id = ?1",
    nativeQuery = true)
    float getTotalBalance(Long userId);
}

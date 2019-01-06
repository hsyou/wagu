package com.hsyou.wagu.repository;

import com.hsyou.wagu.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByUid(String uid);
}

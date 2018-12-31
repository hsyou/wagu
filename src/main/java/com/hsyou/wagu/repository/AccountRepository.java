package com.hsyou.wagu.repository;

import com.hsyou.wagu.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

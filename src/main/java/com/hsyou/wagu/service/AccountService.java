package com.hsyou.wagu.service;

import com.hsyou.wagu.exception.CustomNotFoundException;
import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    public Account getAccount(long id){
        Optional<Account> optAccount = accountRepository.findById(id);
        if(optAccount.isPresent()){
            return optAccount.get();
        }else{
            throw new CustomNotFoundException("Account를 찾을 수 없습니다.");
        }
    }

    public Optional<Account> getAccountByUid(String uid){
        return accountRepository.findAccountByUid(uid);
    }

    public void updateAccountName (long id, String name) {

        Optional<Account> optAccount = accountRepository.findById(id);
        if(optAccount.isPresent()){
            optAccount.get().setName(name);
            accountRepository.save(optAccount.get());
        }else{
            throw new CustomNotFoundException("Account를 찾을 수 없습니다.");
        }
    }

}

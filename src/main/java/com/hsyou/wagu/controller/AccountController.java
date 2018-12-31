package com.hsyou.wagu.controller;

import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.service.AccountService;
import com.hsyou.wagu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("{id}")
    public ResponseEntity<Account> getAccount(@PathVariable long id){


        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping("")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account){
        return ResponseEntity.ok(accountService.saveAccount(account));
    }

    @PutMapping("")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        return ResponseEntity.ok(accountService.saveAccount(account));
    }



}

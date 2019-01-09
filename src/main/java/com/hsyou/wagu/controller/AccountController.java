package com.hsyou.wagu.controller;

import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.model.AccountDTO;
import com.hsyou.wagu.service.AccountService;
import com.hsyou.wagu.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.google.api.plus.Person;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/account")
//@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("{id}")
    public ResponseEntity<Account> getAccount(@PathVariable long id){


        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PutMapping("")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        return ResponseEntity.ok(accountService.saveAccount(account));
    }

    @PutMapping("/name")
    public ResponseEntity<String> updateName (@RequestBody AccountDTO accountDTO){
        long id = 0;
        try{
            id= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized");
        }
        accountService.updateAccountName(id, accountDTO.getName());
        return ResponseEntity.ok("ok");

    }




}

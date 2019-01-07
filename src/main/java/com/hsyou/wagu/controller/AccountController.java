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
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/join")
    public String join () throws IOException {

        return jwtTokenProvider.getLoginURL();
    }

    @GetMapping("/googlecallback")
    public ResponseEntity<String> callback(@RequestParam String code, HttpServletRequest request,HttpServletResponse response){

        try {

            Person profile = jwtTokenProvider.getProfileFromAuthServer(code);

            System.out.println(profile.getId());
            Optional<Account> accountByUid = accountService.getAccountByUid(profile.getId());
            Account rstAccount;
            if (accountByUid.isPresent()) {
                rstAccount = accountByUid.get();
            } else {
                rstAccount = accountService.saveAccount(Account.CreateAccountFromProfile(profile));
            }

            String token = jwtTokenProvider.createJWT(rstAccount);

            HttpHeaders headers = new HttpHeaders();
            if (rstAccount.getName() == null) {
                headers.setLocation(new URI("http://localhost:8080/account/info"));
            } else {
                headers.setLocation(new URI("http://localhost:8080/done"));
            }
            response.addCookie(new Cookie("token", token));

            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }catch (URISyntaxException ex){
            return new ResponseEntity<>(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
        } catch (Exception ex){
            System.out.println(ex.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

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

    @PutMapping("/name")
    public ResponseEntity<String> updateName (@RequestBody AccountDTO accountDTO){
        try{
            long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
//            System.out.println(tmp);

//            long id = jwtTokenProvider.validateTokenAndGetId(tmp);
            accountService.updateAccountName(id, accountDTO.getName());
            return ResponseEntity.ok("ok");
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }




}

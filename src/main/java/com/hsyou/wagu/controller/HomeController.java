package com.hsyou.wagu.controller;

import com.hsyou.wagu.exception.CustomNotFoundException;
import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.model.Comment;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.repository.AccountRepository;
import com.hsyou.wagu.repository.CommentRepository;
import com.hsyou.wagu.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/home")
public class HomeController {


    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }


}

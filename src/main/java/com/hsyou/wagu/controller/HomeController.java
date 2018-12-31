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
@RequestMapping("/")
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

    @GetMapping("/test")
    public String test(){
        Account account1 = new Account();
        account1.setName("post_writer");


        Account account2 = new Account();
        account2.setName("comment_writer");


        Account postW = accountRepository.save(account1);
        Account commW = accountRepository.save(account2);

        //When

        Post post = new Post();
        post.setTitle("test");
        post.setContents("hello");
        post.setWriter(postW);

        Post newPo = postRepository.save(post);

        Comment comment = new Comment();
        comment.setContents("wow");
        comment.setPost(newPo);
        comment.setWriter(commW);

        Comment newC = commentRepository.save(comment);
        return "";
    }

    @GetMapping("/test2")
    public String test2(){
        Optional<Post> byId = postRepository.findById(1l);

        postRepository.delete(byId.get());
        return "1";
    }

}

package com.hsyou.wagu.service;

import com.hsyou.wagu.exception.CustomNotFoundException;
import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.model.Comment;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.repository.AccountRepository;
import com.hsyou.wagu.repository.CommentRepository;
import com.hsyou.wagu.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;


    public Comment saveComment(Comment comment,long postId, long accountId){
        Optional<Account> optAccount = accountRepository.findById(accountId);
        Optional<Post> optPost = postRepository.findById(postId);
        if(!optAccount.isPresent()){
            throw new CustomNotFoundException("Account not found");
        }
        if(!optPost.isPresent()){
            throw new CustomNotFoundException("Post not found");
        }
        Comment created = Comment.createComment(comment, optAccount.get(), optPost.get());

        return commentRepository.save(created);
    }

    public Comment getComment(long id){
        Optional<Comment> optComment = commentRepository.findById(id);
        if(optComment.isPresent()){
            return optComment.get();
        }else{
            throw new CustomNotFoundException("comment를 찾을 수 없습니다.");
        }
    }

    public Comment removeComment(long id){
        Optional<Comment> optComment = commentRepository.findById(id);
        if(optComment.isPresent()){
            optComment.get().setRemoved(true);
            return commentRepository.save(optComment.get());
        }else{
            throw new CustomNotFoundException("comment를 찾을 수 없습니다.");
        }
    }
}

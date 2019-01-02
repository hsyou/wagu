package com.hsyou.wagu.service;

import com.hsyou.wagu.exception.CustomNotFoundException;
import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.model.PostDTO;
import com.hsyou.wagu.repository.AccountRepository;
import com.hsyou.wagu.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    Business logic 처리
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;


    public PostDTO savePost(Post post, long accountId){
        Optional<Account> optAccount = accountRepository.findById(accountId);
        if(!optAccount.isPresent()){
            throw new CustomNotFoundException("Account");
        }
        post.addPost(optAccount.get());
        Post created = postRepository.save(post);


        return created.toDTO();
    }

    public PostDTO getPost(long id){
        Optional<Post> optPost = postRepository.findById(id);
        if(!optPost.isPresent()){
            throw new CustomNotFoundException("post를 찾을 수 없습니다.");
        }
        return optPost.get().toDTO();
    }

    public Post removePost(long id){
        Optional<Post> optPost = postRepository.findById(id);
        if(optPost.isPresent()){
            optPost.get().setRemoved(true);
            return postRepository.save(optPost.get());
        }else{
            throw new CustomNotFoundException("post를 찾을 수 없습니다.");
        }
    }







}

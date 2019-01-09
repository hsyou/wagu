package com.hsyou.wagu.service;

import com.hsyou.wagu.exception.CustomNotFoundException;
import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.model.PostDTO;
import com.hsyou.wagu.repository.AccountRepository;
import com.hsyou.wagu.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


    public PostDTO savePost(PostDTO postdto){
        long accountId=0;
        try {
            accountId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        }catch(Exception ex){

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized");
        }
        Optional<Account> optAccount = accountRepository.findById(accountId);
        if(!optAccount.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found.");
        }
        Post post = postdto.toEntity();
        post.addPost(optAccount.get());
        Post created = postRepository.save(post);

        return created.toDTO();
    }

    public Page<PostDTO> listPost (Pageable pageable){
        return postRepository.findAll(pageable).map(p -> p.toDTO());
    }

    public PostDTO getPost(long id){
        Optional<Post> optPost = postRepository.findById(id);
        if(!optPost.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Not Found.");
        }
        return optPost.get().toDTO();
    }

    public Post removePost(long id){
        long accountId = 0;
        try{
             accountId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized");
        }

        Optional<Post> optPost = postRepository.findById(id);
        if(optPost.isPresent()){
            if(optPost.get().getWriter().getId() != accountId){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized, Not a Writer");
            }
            optPost.get().setRemoved(true);
            return postRepository.save(optPost.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Not Found.");
        }
    }







}

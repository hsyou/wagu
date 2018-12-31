package com.hsyou.wagu.service;

import com.hsyou.wagu.exception.CustomNotFoundException;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    Business logic 처리
 */
@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository=postRepository;
    }

    public Post savePost(Post post){
        return postRepository.save(post);
    }

    public Post getPost(long id){
        Optional<Post> optPost = postRepository.findById(id);
        if(optPost.isPresent()){
            return optPost.get();
        }else{
            throw new CustomNotFoundException("post를 찾을 수 없습니다.");
        }
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

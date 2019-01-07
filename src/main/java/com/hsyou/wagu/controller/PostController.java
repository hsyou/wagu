package com.hsyou.wagu.controller;

import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.model.PostDTO;
import com.hsyou.wagu.repository.PostRepository;
import com.hsyou.wagu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("s")
    public String test(){

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable long id){

        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping("")
    public ResponseEntity<PostDTO> savePost(@RequestBody Post post){
        long accountId = 1L;
        return ResponseEntity.ok(postService.savePost(post, accountId));
    }

//    @PutMapping("")
//    public ResponseEntity<Post> updatePost(@RequestBody Post post){
//        return ResponseEntity.ok(postService.savePost(post, a));
//    }


    @DeleteMapping("{id}")
    public ResponseEntity<Post> removePost(@PathVariable long id) {
        return ResponseEntity.ok(postService.removePost(id));
    }


}

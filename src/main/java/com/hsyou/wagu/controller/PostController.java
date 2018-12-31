package com.hsyou.wagu.controller;

import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.repository.PostRepository;
import com.hsyou.wagu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("{id}")
    public ResponseEntity<Post> getPost(@PathVariable long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping("")
    public ResponseEntity<Post> savePost(@RequestBody Post post){
        return ResponseEntity.ok(postService.savePost(post));
    }

    @PutMapping("")
    public ResponseEntity<Post> updatePost(@RequestBody Post post){
        return ResponseEntity.ok(postService.savePost(post));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Post> removePost(@PathVariable long id) {
        return ResponseEntity.ok(postService.removePost(id));
    }


}

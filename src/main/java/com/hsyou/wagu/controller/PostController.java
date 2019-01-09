package com.hsyou.wagu.controller;

import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.model.PostDTO;
import com.hsyou.wagu.repository.PostRepository;
import com.hsyou.wagu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable long id){

        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping("")
    public ResponseEntity<PostDTO> savePost(@RequestBody PostDTO post){
        return ResponseEntity.ok(postService.savePost(post));

    }

//    @PutMapping("")
//    public ResponseEntity<Post> updatePost(@RequestBody Post post){
//        return ResponseEntity.ok(postService.savePost(post, a));
//    }

    @GetMapping("/list")
    public ResponseEntity<Page<PostDTO>> listPost(Pageable pageable){
        return ResponseEntity.ok(postService.listPost(pageable));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Post> removePost(@PathVariable long id) {
        return ResponseEntity.ok(postService.removePost(id));
    }


}

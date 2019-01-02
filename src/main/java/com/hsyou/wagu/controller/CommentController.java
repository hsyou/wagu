package com.hsyou.wagu.controller;

import com.hsyou.wagu.model.Comment;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.service.CommentService;
import com.hsyou.wagu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


//    @GetMapping("{id}")
//    public ResponseEntity<Comment> getComment(@PathVariable long id){
//        return ResponseEntity.ok(commentService.getComment(id));
//    }
//
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment, @PathVariable long postId){
        long accountId = 1L;

        return ResponseEntity.ok(commentService.saveComment(comment, postId, accountId));
    }
//
//    @PutMapping("")
//    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment){
//        return ResponseEntity.ok(commentService.saveComment(comment));
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Comment> removeComment(@PathVariable long id) {
//        return ResponseEntity.ok(commentService.removeComment(id));
//    }


}

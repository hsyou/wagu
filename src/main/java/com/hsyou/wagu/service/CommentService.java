package com.hsyou.wagu.service;

import com.hsyou.wagu.exception.CustomNotFoundException;
import com.hsyou.wagu.model.Comment;
import com.hsyou.wagu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository=commentRepository;
    }

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
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

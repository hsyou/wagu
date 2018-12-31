package com.hsyou.wagu.repository;

import com.hsyou.wagu.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

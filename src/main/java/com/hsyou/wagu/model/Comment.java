package com.hsyou.wagu.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 255, nullable = false)
    private String contents;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @Column(nullable = false)
    private boolean removed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_writer", referencedColumnName = "id")
    private Account writer;

    public static Comment createComment(Comment comment, Account account, Post post) {
        comment.setWriter(account);
        comment.setPost(post);

        if(!account.getComments().contains(comment)){
            account.getComments().add(comment);
        }

        if(!post.getComments().contains(comment)){
            post.getComments().add(comment);
            post.increaseCommentCount();
        }
        return comment;
    }
}

package com.hsyou.wagu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @CreationTimestamp
    private LocalDateTime created;

    public static LikePost createLikePost(Post post, Account account){
        LikePost likePost = new LikePost();
        likePost.setAccount(account);
        if(!account.getLikePosts().contains(likePost)){
            account.getLikePosts().add(likePost);
        }
        likePost.setPost(post);
        if(!post.getLikePosts().contains(likePost)){
            post.getLikePosts().add(likePost);
            post.increaseLikeCount();
        }
        return likePost;
    }
}

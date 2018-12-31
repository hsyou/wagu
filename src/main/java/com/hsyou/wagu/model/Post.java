package com.hsyou.wagu.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length=255, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;
    private String[] imgPath;
    @Column(nullable = false)
    private int commentCount = 0;
    @Column(nullable = false)
    private int likeCount =0;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @Column(nullable = false)
    private boolean removed = false;

    @ManyToOne
    @JoinColumn(name = "post_writer", referencedColumnName = "id")
    private Account writer;
    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "post")
    private Set<LikePost> likePosts= new HashSet<>();

    public static Post createPost(Post post, Account account){
        post.setWriter(account);
        if(!account.getPosts().contains(post)){
            account.getPosts().add(post);
        }
        return post;
    }
    public void increaseCommentCount(){
        this.commentCount+=1;
    }
    public void increaseLikeCount(){
        this.likeCount+=1;
    }

}

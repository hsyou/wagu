package com.hsyou.wagu.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private long id;
    private String contents;
    private String[] imgPath;
    private int commentCount = 0;
    private int likeCount =0;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean removed = false;

    private AccountDTO writer;
    private Set<Comment> comments = new HashSet<>();
    private Set<LikePost> likePosts= new HashSet<>();

    public Post toEntity(){
        Post post = Post.builder()
                .id(id)
                .contents(contents)
                .imgPath(imgPath)
                .commentCount(commentCount)
                .likeCount(likeCount)
                .created(created)
                .updated(updated)
                .removed(removed)
                .comments(comments)
                .likePosts(likePosts)
                .build();
        if(writer !=null){
            post.setWriter(writer.toEntity());
        }
        return post;
    }

}

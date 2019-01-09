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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_writer", referencedColumnName = "id")
    private Account writer;
    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "post")
    private Set<LikePost> likePosts= new HashSet<>();

    public void addPost(Account account){
        this.setWriter(account);
    }
    public void increaseCommentCount(){
        this.commentCount+=1;
    }
    public void increaseLikeCount(){
        this.likeCount+=1;
    }

    public PostDTO toDTO(){
        return PostDTO.builder()
                .id(this.getId())
                .contents(this.getContents())
                .imgPath(this.getImgPath())
                .writer(this.getWriter().toDTO())
                .build();
    }
}

package com.hsyou.wagu.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    private String img;
    @OneToMany(mappedBy = "writer")
    private Set<Post> posts = new HashSet<>();
    @OneToMany(mappedBy = "writer")
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "account")
    private Set<LikePost> likePosts = new HashSet<>();

    public AccountDTO toDTO(){
        return AccountDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .email(this.getEmail())
                .img(this.getImg())
                .build();
    }
}

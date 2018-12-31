package com.hsyou.wagu.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String img;
    @OneToMany(mappedBy = "writer")
    private Set<Post> posts = new HashSet<>();
    @OneToMany(mappedBy = "writer")
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "account")
    private Set<LikePost> likePosts = new HashSet<>();
//


}

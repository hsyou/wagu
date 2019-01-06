package com.hsyou.wagu.model;

import lombok.*;
import org.springframework.social.google.api.plus.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(nullable = false)
    private String email;
    private String img;

    private String uid;
    @Enumerated(EnumType.ORDINAL)
    private AuthProvider provider;

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

    public static Account CreateAccountFromProfile(Person profile){
        return Account.builder()
                .email(profile.getAccountEmail())
                .uid(profile.getId())
                .provider(AuthProvider.GOOGLE)
                .build();
    }

}

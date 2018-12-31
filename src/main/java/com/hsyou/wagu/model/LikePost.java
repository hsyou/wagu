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
    @JoinColumn(name = "post_id", columnDefinition = "likePosts")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "account_id", columnDefinition = "likePosts")
    private Account account;
    @CreationTimestamp
    private LocalDateTime created;
}

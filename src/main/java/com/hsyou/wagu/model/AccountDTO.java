package com.hsyou.wagu.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private long id;
    private String name;
    private String email;
    private String img;


}

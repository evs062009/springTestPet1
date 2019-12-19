package com.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue()
    private long id;

    @Column(unique = true)
    private String login;

    public User(String login) {
        this.login = login;
    }
}

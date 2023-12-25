package com.example.rwsignup.dto;

import javax.persistence.*;

@Table(name = "\"tbl_Users\"")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"UserID\"")
    private Integer id;
    @Column(name = "\"FulName\"")
    private String username;
    @Column(name = "\"Email\"")
    private String email;
    @Column(name = "\"Password\"")
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

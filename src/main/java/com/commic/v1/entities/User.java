package com.commic.v1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    @Column(name = "full_name")
    private String fullName;
    private String phone;
    private Integer point;
    private String role;
    private String avatar;
    private String otp;
    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;
    @OneToMany(mappedBy = "user")
    private Set<Rating> ratings;
    @OneToMany(mappedBy = "user")
    private Set<RedeemReward> redeemRewards;
    @OneToMany(mappedBy = "user")
    private Set<History> histories;
}

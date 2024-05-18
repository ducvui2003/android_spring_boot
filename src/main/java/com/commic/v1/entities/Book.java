package com.commic.v1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String author;
    private String description;
    private Integer state;

    @ManyToMany(mappedBy = "books")
    private Set<Category> categories;

    private String thumbnail;

    @OneToMany(mappedBy = "book")
    private Set<Chapter> chapters;

    @OneToMany(mappedBy = "book")
    private Set<Statistical> statisticals;
}

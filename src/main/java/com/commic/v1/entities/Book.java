package com.commic.v1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
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

    @ManyToMany(mappedBy = "books")
    private Set<Category> categories;
    private String thumbnail;
    private String state;

    @OneToMany(mappedBy = "book")
    private Set<Chapter> chapters;
}

package com.commic.v1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Book book;
    private String name;
    @Column(name = "publish_date")
    private Date publishDate;
    private Integer view;
    private Float rating;
    @OneToMany(mappedBy = "chapter")
    private Set<ChapterContent> chapterContent;
    @OneToMany(mappedBy = "chapter")
    private Set<Comment> comments;
    @OneToMany(mappedBy = "chapter")
    private Set<Rating> ratings;
    @OneToMany(mappedBy = "chapter")
    private Set<History> histories;
}

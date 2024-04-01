package com.commic.v1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "statistics")
public class Statistic {
    private Integer id;
//    @Column(name = "book_id")
    private Book book;
}

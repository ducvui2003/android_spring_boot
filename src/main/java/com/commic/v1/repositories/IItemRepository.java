package com.commic.v1.repositories;

import com.commic.v1.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemRepository extends JpaRepository<Item, Integer> {
    Page<Item> findAll(Pageable pageable);
}

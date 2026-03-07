package com.food.repository;

import com.food.model.Menu_Photos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuPhotosRepository extends JpaRepository<Menu_Photos, Long> {
}
package com.mgsystems.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgsystems.dscommerce.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}

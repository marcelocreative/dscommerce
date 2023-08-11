package com.mgsystems.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgsystems.dscommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

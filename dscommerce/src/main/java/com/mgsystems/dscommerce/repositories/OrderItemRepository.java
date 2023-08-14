package com.mgsystems.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgsystems.dscommerce.entities.OrderItem;
import com.mgsystems.dscommerce.entities.OrderItemPK;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}

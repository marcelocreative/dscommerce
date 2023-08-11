package com.mgsystems.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mgsystems.dscommerce.dto.OrderDTO;
import com.mgsystems.dscommerce.entities.Order;
import com.mgsystems.dscommerce.repositories.OrderRepository;
import com.mgsystems.dscommerce.services.exceptions.ResourceNotFoundException;


@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		
		Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		
		return new OrderDTO(order);
	}

}

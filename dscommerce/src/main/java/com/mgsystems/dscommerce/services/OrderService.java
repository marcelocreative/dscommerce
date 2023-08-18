package com.mgsystems.dscommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mgsystems.dscommerce.dto.OrderDTO;
import com.mgsystems.dscommerce.dto.OrderItemDTO;
import com.mgsystems.dscommerce.entities.Order;
import com.mgsystems.dscommerce.entities.OrderItem;
import com.mgsystems.dscommerce.entities.OrderStatus;
import com.mgsystems.dscommerce.entities.Product;
import com.mgsystems.dscommerce.entities.User;
import com.mgsystems.dscommerce.repositories.OrderItemRepository;
import com.mgsystems.dscommerce.repositories.OrderRepository;
import com.mgsystems.dscommerce.repositories.ProductRepository;
import com.mgsystems.dscommerce.services.exceptions.ResourceNotFoundException;


@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		
		Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		authService.validateSelfOrAdmin(order.getClient().getId());
		
		return new OrderDTO(order);
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		
		Order order= new Order();
		
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user);

		for(OrderItemDTO itemDTO : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDTO.getProductId());
			OrderItem item= new OrderItem(itemDTO.getQuantity(), product.getPrice(),order, product);
			order.getItems().add(item);
			
		}
		
		
		repository.save(order);
		
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}

}

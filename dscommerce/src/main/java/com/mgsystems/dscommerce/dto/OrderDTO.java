package com.mgsystems.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mgsystems.dscommerce.entities.Order;
import com.mgsystems.dscommerce.entities.OrderItem;
import com.mgsystems.dscommerce.entities.OrderStatus;

public class OrderDTO {
	
	private Long id;
	private Instant moment;
	private OrderStatus status;
	
	private ClientDTO client;
	private PaymentDTO payment;
	private List<OrderItemDTO> itens= new ArrayList<>();
	
	public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
		this.id = id;
		this.moment = moment;
		this.status = status;
		this.client = client;
		this.payment = payment;
	}
	
	public OrderDTO(Order entity) {
		id = entity.getId();
		moment = entity.getMoment();
		status = entity.getStatus();
		client = new ClientDTO(entity.getClient());
		payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment()); 
		
		for(OrderItem item : entity.getItens()) {
			
			itens.add(new OrderItemDTO(item));
			
		}
	}
	
	public Long getId() {
		return id;
	}
	public Instant getMoment() {
		return moment;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public ClientDTO getClient() {
		return client;
	}
	public PaymentDTO getPayment() {
		return payment;
	}
	public List<OrderItemDTO> getItens() {
		return itens;
	}
	
	public Double getTotal() {
		
		double sum= 0.0;
		
		for(OrderItemDTO item : itens) {
			
			sum += item.getSubTotal();
			
		}
		
		return sum;
		
	}

}

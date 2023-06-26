package com.mgsystems.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mgsystems.dscommerce.dto.ProductDTO;
import com.mgsystems.dscommerce.entities.Product;
import com.mgsystems.dscommerce.repositories.ProductRepository;
import com.mgsystems.dscommerce.services.exceptions.DatabaseException;
import com.mgsystems.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id){
		
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		
		return new ProductDTO(product);
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
	
		Page<Product> result = repository.findAll(pageable);
		
		return result.map(x -> new ProductDTO(x));
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity= new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		
		try {
			Product entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			
			return new ProductDTO(entity);
			
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			
			throw new DatabaseException("Falha de integridade referencial");
			
		}
		
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		
	}

	
}

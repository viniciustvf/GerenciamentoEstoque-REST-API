package com.trier.gerenciamentoestoque.models;


import com.trier.gerenciamentoestoque.models.dto.ProductMovementDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity(name = "productMovement")
public class ProductMovement {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_productMovement")
	private Integer id;

	@Column(name = "price_product")
	private Double price;
	
	@Column(name = "quantity_movement")
	private Integer quantity;
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private Movement movement;
	
	public ProductMovement(ProductMovementDTO dto, Product product, Movement movement) {
		this(dto.getId(),dto.getPrice(), dto.getQuantity(), product, movement); 
	}
	
	public ProductMovementDTO toDTO() {
	    return new ProductMovementDTO(getId(), getQuantity(), getPrice(), product.getId(), product.getName(), movement.getId(), movement.getMovementType().name()); 
	}
}
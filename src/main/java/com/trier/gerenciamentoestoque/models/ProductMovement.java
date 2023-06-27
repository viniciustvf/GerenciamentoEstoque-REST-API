package com.trier.gerenciamentoestoque.models;


import com.trier.gerenciamentoestoque.models.dto.MovementDTO;
import com.trier.gerenciamentoestoque.models.dto.ProductMovementDTO;
import com.trier.gerenciamentoestoque.utils.DateUtils;
import com.trier.gerenciamentoestoque.utils.EnumUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
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

	@Column(name = "quantity_movement")
	@Size(max = 4)
	private Integer quantity;
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private Movement movement;

	public ProductMovement(ProductMovementDTO dto, Product product, Movement movement) {
		this(dto.getId(), dto.getQuantity(), product, movement); 
	}
	
	public ProductMovementDTO toDTO() {
	    return new ProductMovementDTO(getId(), getQuantity(), product.getId(), product.getName(), movement.getId(), movement.getMovementType().getDescription()); 
	}
}
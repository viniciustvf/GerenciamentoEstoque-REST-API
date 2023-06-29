package com.trier.gerenciamentoestoque.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductMovementDTO {

	private Integer id;

	private Integer quantity;
	
	private Double price;
	
	private Integer productId;
	
	private String productName;
	
	private Integer movementId;
	
	private String movementType;
	
}

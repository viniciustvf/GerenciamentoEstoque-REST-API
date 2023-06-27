package com.trier.gerenciamentoestoque.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDTO {

	private Integer id;

	private String name;
	
	private Double price;

	private Integer barcode;
	
	private Integer amount;
	
	private Integer categoryId;
	
	private String categoryDescription;
	
}

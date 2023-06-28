package com.trier.gerenciamentoestoque.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductMovementDateDTO {

		private String date;
		
		private Integer productSize;
		
		private List<ProductDTO> products;
	
}

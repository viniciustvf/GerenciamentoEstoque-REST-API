package com.trier.gerenciamentoestoque.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OutputDTO {

	private Integer id;

	private Integer clientId;
	
	private String clientName;
	
	private Integer sellerId;
	
	private String sellerName;
	
}

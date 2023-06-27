package com.trier.gerenciamentoestoque.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovementDTO {

	private Integer id;
	
	private String dateTime;
	
    private String movementTypeString;
    
	private Integer entryId;
	
	private Integer outputId;
	
}

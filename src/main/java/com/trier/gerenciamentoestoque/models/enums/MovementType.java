package com.trier.gerenciamentoestoque.models.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "movement_type")
@AllArgsConstructor
@Getter
public enum MovementType {
	
	ENTRY(1, "Entrada"),
    OUTPUT(2, "Saída");

	@Id
    private Integer id;

    @Column(name = "description")
    private String description;

}

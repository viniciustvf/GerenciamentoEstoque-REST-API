package com.trier.gerenciamentoestoque.models;


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

}
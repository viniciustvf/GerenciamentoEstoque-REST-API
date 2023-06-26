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
@Entity(name = "product")
public class Product {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product")
	private Integer id;
	
	@Column(name = "name_product")
	private String name;
	
	@Column(name = "price_product")
	@Size(max = 7)
	private Double price;
	
	@Column(name = "barcode_product", unique = true)
	private Integer barcode;
	
	@Column(name = "amount_product")
	private Integer amount;
	
	@ManyToOne
	private Category category;

}
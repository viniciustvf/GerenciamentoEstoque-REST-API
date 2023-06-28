package com.trier.gerenciamentoestoque.models;

import com.trier.gerenciamentoestoque.models.dto.ProductDTO;

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
	private Double price;
	
	@Column(name = "barcode_product", unique = true)
	private Integer barcode;
	
	@Column(name = "amount_product")
	private Integer amount;
	
	@ManyToOne
	private Category category;
	
	public Product (ProductDTO dto, Category category) {
		this(dto.getId(), dto.getName(), dto.getPrice(), dto.getBarcode(), dto.getAmount(), category); 
	}
	
	public ProductDTO toDTO() {
	    return new ProductDTO(getId(), getName(), getPrice(), getBarcode(), getAmount(), category.getId(), category.getDescription()); 
	}
}
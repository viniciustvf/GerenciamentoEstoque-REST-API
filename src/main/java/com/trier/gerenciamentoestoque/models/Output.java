package com.trier.gerenciamentoestoque.models;


import com.trier.gerenciamentoestoque.models.dto.MovementDTO;
import com.trier.gerenciamentoestoque.models.dto.OutputDTO;
import com.trier.gerenciamentoestoque.utils.DateUtils;
import com.trier.gerenciamentoestoque.utils.EnumUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "output")
public class Output {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_output")
	private Integer id;
	
	@ManyToOne
	private Client client;
	
	@ManyToOne
	private Seller seller;
	
    public Output (Client client, OutputDTO dto, Seller seller) {
		this(dto.getId(), client, seller); 
	}
	
	public OutputDTO toDTO() {
	    return new OutputDTO(getId(), client.getId(), client.getName(), seller.getId(), seller.getName()); 
	}
	
}
package com.trier.gerenciamentoestoque.models.dto;

import java.util.List;

import com.trier.gerenciamentoestoque.models.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientsOfSellerDTO {

	private Integer sellerId;
	
	private String sellerRegistration;
	
	private Integer clientsSize;
	
	private List<Client> clients;
	
}

package com.trier.gerenciamentoestoque.services;

import com.trier.gerenciamentoestoque.models.dto.ClientsOfSellerDTO;
import com.trier.gerenciamentoestoque.models.dto.ProductMovementDateDTO;

public interface ReportService {

	ProductMovementDateDTO findProductByDate(String date);
	
	ClientsOfSellerDTO findClientsOfSeller (Integer sellerId);
	
	Double findTotalValueOfProducts();

}

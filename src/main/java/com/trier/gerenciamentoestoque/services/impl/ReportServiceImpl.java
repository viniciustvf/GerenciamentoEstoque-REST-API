package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.Seller;
import com.trier.gerenciamentoestoque.models.dto.ClientsOfSellerDTO;
import com.trier.gerenciamentoestoque.models.dto.ProductDTO;
import com.trier.gerenciamentoestoque.models.dto.ProductMovementDateDTO;
import com.trier.gerenciamentoestoque.services.MovementService;
import com.trier.gerenciamentoestoque.services.OutputService;
import com.trier.gerenciamentoestoque.services.ProductMovementService;
import com.trier.gerenciamentoestoque.services.ProductService;
import com.trier.gerenciamentoestoque.services.ReportService;
import com.trier.gerenciamentoestoque.services.SellerService;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;
import com.trier.gerenciamentoestoque.utils.DateUtils;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private MovementService movementService;

	@Autowired
	private ProductMovementService productMovementService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OutputService outputService;

	@Autowired
	private SellerService sellerService;

	@Override
	public ProductMovementDateDTO findProductByDate(String date) {
		List<Movement> movements = movementService.findByDateTimeContainsDate(DateUtils.strToZonedDate(date));
		List<ProductDTO> products = movements.stream().flatMap(movement -> {
			try {
				return productMovementService.findByMovement(movement).stream();
			} catch (ObjectNotFound e) {
				return Stream.empty();
			}
		}).map(productMovement -> productMovement != null ? productMovement.getProduct().toDTO() : null)
				.collect(Collectors.toList());
		return new ProductMovementDateDTO(date, products.size(), products);
	}

	@Override
	public ClientsOfSellerDTO findClientsOfSeller(Integer sellerId) {
		Seller seller = sellerService.findById(sellerId);
		if (seller == null) {
			throw new ObjectNotFound("Vendedor não encontrado");
		}

		List<Output> outputs = outputService.findBySeller(seller);

		List<Client> clients = outputs.stream().map(Output::getClient).collect(Collectors.toList());

		ClientsOfSellerDTO responseDTO = new ClientsOfSellerDTO(sellerId, seller.getRegistration(), clients.size(),
				clients);
		return responseDTO;
	}

	@Override
	public Double findTotalValueOfProducts() {
		Double value = productService.findTotalValueOfProducts();
		return value;
	}

}

package com.trier.gerenciamentoestoque.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.trier.gerenciamentoestoque.services.SellerService;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;
import com.trier.gerenciamentoestoque.utils.DateUtils;




@RestController
@RequestMapping("/reports")
public class ReportResource {
	
	@Autowired
	MovementService movementService;
	
	@Autowired
	ProductMovementService productMovementService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OutputService outputService;
	
	@Autowired
	SellerService sellerService;
	
	//TODOS OS PRODUTOS MOVIMENTADOS EM UMA DATA
	@GetMapping("/products-movement-by-date/{date}")
	public ResponseEntity<ProductMovementDateDTO> findProductByDate(@PathVariable String date) {
		List<Movement> movements = movementService.findByDateTimeContainsCurrentDate(DateUtils.strToZonedDate(date));
		List<ProductDTO> products = movements.stream().flatMap(movement -> {
			try {
				return productMovementService.findByMovement(movement).stream();
			} 
			catch (ObjectNotFound e) {
				return Stream.empty();
			}
		}).map(productMovement -> productMovement != null ? productMovement.getProduct().toDTO() : null)
		  .collect(Collectors.toList());
		
		return ResponseEntity.ok(new ProductMovementDateDTO(date, products.size(), products));
	}
	
	//TODOS OS CLIENTES QUE COMPRARAM COM UM VENDEDOR 
	@GetMapping("/clients-of-seller/{sellerId}")
	public ResponseEntity<ClientsOfSellerDTO> findClientsOfSeller(@PathVariable Integer sellerId) {
	    Seller seller = sellerService.findById(sellerId);
	    if (seller == null) {
	        return ResponseEntity.notFound().build();
	    }

	    List<Output> outputs = outputService.findBySeller(seller);

	    List<Client> clients = outputs.stream()
	            .map(Output::getClient)
	            .collect(Collectors.toList());

	    ClientsOfSellerDTO responseDTO = new ClientsOfSellerDTO(sellerId, seller.getRegistration(), clients.size(), clients);
	    return ResponseEntity.ok(responseDTO);
	}	
	
	//VALOR TOTAL DE TODOS OS PRODUTOS ARMAZENADOS
	@GetMapping("/total-value-of-products")
	public ResponseEntity<Double> findTotalValueOfProducts() {
		Double value = productService.findTotalValueOfProducts();
		return ResponseEntity.ok(value);		
	}
}

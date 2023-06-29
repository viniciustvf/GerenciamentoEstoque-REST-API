package com.trier.gerenciamentoestoque.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trier.gerenciamentoestoque.models.dto.ClientsOfSellerDTO;
import com.trier.gerenciamentoestoque.models.dto.ProductMovementDateDTO;
import com.trier.gerenciamentoestoque.services.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportResource {

	@Autowired
	private ReportService service;

	// TODOS OS PRODUTOS MOVIMENTADOS EM UMA DATA
	@Secured({ "ROLE_USER" })
	@GetMapping("/products-movement-by-date/{date}")
	public ResponseEntity<ProductMovementDateDTO> findProductByDate(@PathVariable String date) {
		return ResponseEntity.ok(service.findProductByDate(date));
	}

	// TODOS OS CLIENTES QUE COMPRARAM COM UM VENDEDOR
	@Secured({ "ROLE_USER" })
	@GetMapping("/clients-of-seller/{sellerId}")
	public ResponseEntity<ClientsOfSellerDTO> findClientsOfSeller(@PathVariable Integer sellerId) {
		return ResponseEntity.ok(service.findClientsOfSeller(sellerId));
	}

	// VALOR TOTAL DE TODOS OS PRODUTOS ARMAZENADOS
	@Secured({ "ROLE_USER" })
	@GetMapping("/total-value-of-products")
	public ResponseEntity<Double> findTotalValueOfProducts() {
		return ResponseEntity.ok(service.findTotalValueOfProducts());
	}
}

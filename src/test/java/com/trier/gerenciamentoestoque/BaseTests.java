package com.trier.gerenciamentoestoque;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.trier.gerenciamentoestoque.services.CategoryService;
import com.trier.gerenciamentoestoque.services.ClientService;
import com.trier.gerenciamentoestoque.services.EntryService;
import com.trier.gerenciamentoestoque.services.MovementService;
import com.trier.gerenciamentoestoque.services.OutputService;
import com.trier.gerenciamentoestoque.services.ProductMovementService;
import com.trier.gerenciamentoestoque.services.ProductService;
import com.trier.gerenciamentoestoque.services.SellerService;
import com.trier.gerenciamentoestoque.services.SupplierService;
import com.trier.gerenciamentoestoque.services.impl.CategoryServiceImpl;
import com.trier.gerenciamentoestoque.services.impl.ClientServiceImpl;
import com.trier.gerenciamentoestoque.services.impl.EntryServiceImpl;
import com.trier.gerenciamentoestoque.services.impl.MovementServiceImpl;
import com.trier.gerenciamentoestoque.services.impl.OutputServiceImpl;
import com.trier.gerenciamentoestoque.services.impl.ProductMovementServiceImpl;
import com.trier.gerenciamentoestoque.services.impl.ProductServiceImpl;
import com.trier.gerenciamentoestoque.services.impl.SellerServiceImpl;
import com.trier.gerenciamentoestoque.services.impl.SupplierServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

	@Bean
	public CategoryService categtoryService() {
		return new CategoryServiceImpl();
	}
	
	@Bean
	public ClientService ClientService() {
		return new ClientServiceImpl();
	}
	
	@Bean
	public EntryService EntryService() {
		return new EntryServiceImpl();
	}
	
	@Bean
	public MovementService MovementService() {
		return new MovementServiceImpl();
	}
	
	@Bean
	public OutputService OutputService() {
		return new OutputServiceImpl();
	}
	
	@Bean
	public ProductMovementService ProductMovementService() {
		return new ProductMovementServiceImpl();
	}
	
	@Bean
	public  ProductService ProductService() {
		return new ProductServiceImpl();
	}
	
	@Bean
	public SellerService SellerService() {
		return new SellerServiceImpl();
	}
	
	@Bean
	public SupplierService SupplierService() {
		return new SupplierServiceImpl();
	}
}

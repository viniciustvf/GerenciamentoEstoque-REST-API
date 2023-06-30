package com.trier.gerenciamentoestoque.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.trier.gerenciamentoestoque.GerenciamentoEstoqueApplication;
import com.trier.gerenciamentoestoque.config.jwt.LoginDTO;
import com.trier.gerenciamentoestoque.models.dto.ProductMovementDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = GerenciamentoEstoqueApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductMovementResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String senha) {
		LoginDTO loginDTO = new LoginDTO(email, senha);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
	    ResponseEntity<String> responseEntity = rest.exchange(
	            "/auth/token",
	            HttpMethod.POST, 
	            requestEntity,
	            String.class
	    );
	    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	    HttpHeaders headersRet = new HttpHeaders();
	    headersRet.setBearerAuth(responseEntity.getBody());
	    return headersRet;
	}
	
	private ResponseEntity<ProductMovementDTO> getProductMovement(String url) {
		return rest.exchange(url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("Email 1", "Senha 1")), 
				ProductMovementDTO.class
			);
	}

	private ResponseEntity<List<ProductMovementDTO>> getProductMovements(String url) {
	    return rest.exchange(
	        url,
	        HttpMethod.GET,
	        new HttpEntity<>(getHeaders("Email 1", "Senha 1")),
	        new ParameterizedTypeReference<List<ProductMovementDTO>>() {}
	    );
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void findByIdTest() {
		ResponseEntity<ProductMovementDTO> response = getProductMovement("/productMovement/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		ProductMovementDTO pm = response.getBody();
		assertEquals(1, pm.getMovementId());
	}
	
	@Test
	@DisplayName("Inserir produto movimento")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void insertProductMovementTest() {
		ProductMovementDTO dto = new ProductMovementDTO(null, 2, 195.0, 2, "Qualquernome", 1, "OUTPUT");
		HttpHeaders headers = getHeaders("Email 1", "Senha 1");
		HttpEntity<ProductMovementDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<ProductMovementDTO> responseEntity = rest.exchange(
	            "/productMovement", 
	            HttpMethod.POST,  
	            requestEntity,    
	            ProductMovementDTO.class   
	    );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ProductMovementDTO pm = responseEntity.getBody();
		assertEquals(199.0, pm.getPrice());
		assertEquals("Produto 2", pm.getProductName());
	}
	
	@Test
	@DisplayName("Alterar produto movimentado")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void updateProductMovementTest() {
		ProductMovementDTO dto = new ProductMovementDTO(null, 10, 199.0, 2, "Qualquernome", 1, "OUTPUT");
		HttpHeaders headers = getHeaders("Email 1", "Senha 1");
		HttpEntity<ProductMovementDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<ProductMovementDTO> responseEntity = rest.exchange(
	            "/productMovement/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            ProductMovementDTO.class   
	    );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ProductMovementDTO pm = responseEntity.getBody();
		assertEquals(199.0, pm.getPrice());
		assertEquals("Produto 2", pm.getProductName());
		assertEquals(10, pm.getQuantity());
	}
	
	@Test
	@DisplayName("Teste deletar produto movimentado")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void deleteTest() {
		HttpHeaders headers = getHeaders("Email 1", "Senha 1");
		HttpEntity<ProductMovementDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
	            "/productMovement/1", 
	            HttpMethod.DELETE,  
	            requestEntity,
	            Void.class   
	    );		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	@DisplayName("Teste listar todos os produtos movimentados")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void listAllTest() {
		ResponseEntity<List<ProductMovementDTO>> response = getProductMovements("/productMovement"); 
		assertEquals(response.getStatusCode(), HttpStatus.OK); 
	}
	
	@Test
	@DisplayName("Buscar por produto")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void findByProductTest() {
		ResponseEntity<List<ProductMovementDTO>> response = getProductMovements("/productMovement/product/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<ProductMovementDTO> pm = response.getBody();
		assertEquals(1, pm.size());
		assertEquals(20.0, pm.get(0).getPrice());
		ResponseEntity<ProductMovementDTO> response2 = getProductMovement("/productMovement/product/3");
	    assertEquals(response2.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Buscar por movimento")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void findByMovememntTest() {
		ResponseEntity<List<ProductMovementDTO>> response = getProductMovements("/productMovement/movement/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<ProductMovementDTO> pm = response.getBody();
		assertEquals(1, pm.size());
		assertEquals("ENTRY", pm.get(0).getMovementType());
		assertEquals("Produto 1", pm.get(0).getProductName());
		ResponseEntity<ProductMovementDTO> response2 = getProductMovement("/productMovement/movement/3");
	    assertEquals(response2.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Buscar por quantidade")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void findByQuantityTest() {
		ResponseEntity<List<ProductMovementDTO>> response = getProductMovements("/productMovement/quantity/2");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<ProductMovementDTO> pm = response.getBody();
		assertEquals(2, pm.size());
		ResponseEntity<ProductMovementDTO> response2 = getProductMovement("/productMovement/quantity/3");
	    assertEquals(response2.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
package com.trier.gerenciamentoestoque.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.trier.gerenciamentoestoque.models.dto.ClientsOfSellerDTO;
import com.trier.gerenciamentoestoque.models.dto.ProductMovementDateDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = GerenciamentoEstoqueApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private HttpHeaders getHeaders(String email, String senha) {
		LoginDTO loginDTO = new LoginDTO(email, senha);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		HttpHeaders headersRet = new HttpHeaders();
		headersRet.setBearerAuth(responseEntity.getBody());
		return headersRet;
	}

	@Test
	@DisplayName("Teste buscar produtos movimentados por data")
	@Sql({ "classpath:/resources/sqls/clean_tables.sql" })
	@Sql({ "classpath:/resources/sqls/category.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	@Sql({ "classpath:/resources/sqls/productMovement.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void findProductByDateTest() {
		HttpHeaders headers = getHeaders("Email 1", "Senha 1");
		HttpEntity<ProductMovementDateDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ProductMovementDateDTO> response = rest.exchange("/reports/products-movement-by-date/01-01-2024",
				HttpMethod.GET, requestEntity, new ParameterizedTypeReference<ProductMovementDateDTO>() {
				});
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ProductMovementDateDTO pm = response.getBody();
		assertEquals(1, pm.getProductSize());

	}

	@Test
	@DisplayName("Teste buscar clientes de um vendedor")
	@Sql({ "classpath:/resources/sqls/clean_tables.sql" })
	@Sql({ "classpath:/resources/sqls/category.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	@Sql({ "classpath:/resources/sqls/productMovement.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void findClientsOfSellerTest() {
		HttpHeaders headers = getHeaders("Email 1", "Senha 1");
		HttpEntity<ClientsOfSellerDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ClientsOfSellerDTO> response = rest.exchange("/reports/clients-of-seller/1", HttpMethod.GET,
				requestEntity, new ParameterizedTypeReference<ClientsOfSellerDTO>() {
				});
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ClientsOfSellerDTO cs = response.getBody();
		assertEquals(1, cs.getClientsSize());

	}

	@Test
	@DisplayName("Teste buscar clientes de um vendedor")
	@Sql({ "classpath:/resources/sqls/clean_tables.sql" })
	@Sql({ "classpath:/resources/sqls/category.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	@Sql({ "classpath:/resources/sqls/productMovement.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void findTotalValueOfProductsTest() {
		HttpHeaders headers = getHeaders("Email 1", "Senha 1");
		HttpEntity<Double> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Double> response = rest.exchange("/reports/total-value-of-products", HttpMethod.GET,
				requestEntity, new ParameterizedTypeReference<Double>() {
				});
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Double value = response.getBody();
		assertEquals(3094.0, value);
	}
}
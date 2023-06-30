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
import com.trier.gerenciamentoestoque.models.dto.UserDTO;



@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = GerenciamentoEstoqueApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	@Test
	@DisplayName("Obter Token")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void getTokenTest() {
		LoginDTO loginDTO = new LoginDTO("Email 1", "Senha 1");
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
		String token = responseEntity.getBody();
		System.out.println("****************"+ token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<UserDTO>> response =  rest.exchange("/user", HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<UserDTO>>() {} , headers);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	
	@Test
	@DisplayName("Obter Token Inválido")
	@Sql({"classpath:/resources/sqls/clean_tables.sql"})
	@Sql({"classpath:/resources/sqls/user.sql"})
	public void getTokenWrongTest() {
		LoginDTO loginDTO = new LoginDTO("Email Errado", "Senha 1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
}

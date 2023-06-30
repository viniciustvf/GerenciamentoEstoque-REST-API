package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.trier.gerenciamentoestoque.BaseTests;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class ClientServiceTest extends BaseTests {

	@Autowired
	ClientService clientService;

	@Test
	@DisplayName("Teste buscar cliente por ID")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void findByIdTest() {
		var client = clientService.findById(1);
		assertNotNull(client);
		assertEquals(1, client.getId());
		assertEquals("Nome 1", client.getName());
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findById(10));
		assertEquals("O cliente 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir cliente")
	void insertClientTest() {
		Client client = new Client(null, "insert", "insert", 20, 48999072751L);
		clientService.insert(client);
		client = clientService.findById(client.getId());
		assertEquals("insert", client.getName());
		assertEquals(48999072751L, client.getNumber());
		Client client2 = new Client(null, "insert", "insert", null, 48899072751L);
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(client2));
		assertEquals("Idade não pode ser nula", exception.getMessage());
		Client client3 = new Client(null, "insert", null, 20, 4879072751L);
		var exception2 = assertThrows(IntegrityViolation.class, () -> clientService.insert(client3));
		assertEquals("CPF não pode ser nulo", exception2.getMessage());
		Client client4 = new Client(null, null, "insert", 20, 4879072751L);
		var exception3 = assertThrows(IntegrityViolation.class, () -> clientService.insert(client4));
		assertEquals("Nome não pode ser nulo", exception3.getMessage());
		Client client5 = new Client(null, "insert", "insert", 20, 4879072751323224L);
		var exception4 = assertThrows(IntegrityViolation.class, () -> clientService.insert(client5));
		assertEquals("Número inválido", exception4.getMessage());
		Client client6 = new Client(null, "insert", "insewt", 20, null);
		var exception5 = assertThrows(IntegrityViolation.class, () -> clientService.insert(client6));
		assertEquals("Número inválido", exception5.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void listAllTest() {
		List<Client> lista = clientService.listAll();
		assertEquals(3, lista.size());
	}

	@Test
	@DisplayName("Teste listar todos sem clientes cadastrados")
	void listAllWrongTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.listAll());
		assertEquals("Nenhum cliente cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar cliente")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void updateClientTest() {
		Client client = new Client(1, "update", "update", 20, 48999072751L);
		clientService.update(client);
		assertEquals(1, client.getId());
		assertEquals("update", client.getName());
	}

	@Test
	@DisplayName("Teste apagar cliente")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void deleteByIdTest() {
		clientService.delete(1);
		List<Client> list = clientService.listAll();
		assertEquals(2, list.size());
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.delete(10));
		assertEquals("O cliente 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar cliente contendo nome")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void findByNameContainingOrderTest() {
		var lista = clientService.findByNameContainingOrderByNameDesc("Nom");
		assertEquals(3, lista.size());
		var exception = assertThrows(ObjectNotFound.class,
				() -> clientService.findByNameContainingOrderByNameDesc("zome"));
		assertEquals("Nenhum nome zome encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar cliente contendo nome")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void findByCpfTest() {
		var lista = clientService.findByCpf("123456789");
		assertEquals(1, lista.get().getId());
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByCpf("123"));
		assertEquals("Cliente não encontrado com CPF 123", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar cliente por idade")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void findByAgeTest() {
		var lista = clientService.findByAge(20);
		assertEquals(1, lista.size());
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByAge(72));
		assertEquals("Nenhum cliente encontrado com 72 anos", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar cliente por idade")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void findByAgeBetweenTest() {
		var lista = clientService.findByAgeBetween(19, 25);
		assertEquals(2, lista.size());
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByAgeBetween(1, 2));
		assertEquals("Nenhum cliente encontrado com idades entre 1 e 2 anos", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar cliente por numero")
	@Sql({ "classpath:/resources/sqls/client.sql" })
	void findByNumberTest() {
		var lista = clientService.findByNumber(123456789L);
		assertEquals(123456789L, lista.get().getNumber());
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByNumber(123456719L));
		assertEquals("Cliente não encontrado com numero 123456719", exception.getMessage());
	}

}
package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.Supplier;
import com.trier.gerenciamentoestoque.models.Supplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.trier.gerenciamentoestoque.BaseTests;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;

@Transactional
public class SupplierServiceTest extends BaseTests {

	@Autowired
	SupplierService service;

	@Test
	@DisplayName("Teste buscar fornecedor por ID")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	void findByIdTest() {
		var supplier = service.findById(1);
		assertNotNull(supplier);
		assertEquals(1, supplier.getId());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("O fornecedor 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir fornecedor")
	void insertSupplierTest() {
		Supplier supplier = new Supplier(null, "insert", "12345");
		service.insert(supplier);
		supplier = service.findById(supplier.getId());
		assertEquals("insert", supplier.getName());
		assertEquals("12345", supplier.getCnpj());

		Supplier supplier2 = new Supplier(null, null, "123456");
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(supplier2));
		assertEquals("O nome do fornecedor(a) não pode ser nulo", exception.getMessage());

		Supplier supplier3 = new Supplier(null, "insert2", null);
		var exceptio2 = assertThrows(IntegrityViolation.class, () -> service.insert(supplier3));
		assertEquals("O CNPJ do fornecedor não pode ser nulo", exceptio2.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	void listAllTest() {
		List<Supplier> lista = service.listAll();
		assertEquals(3, lista.size());
	}

	@Test
	@DisplayName("Teste listar todos sem fornecedores cadastrados")
	void listAllWrongTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum fornecedor cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar fornecedor")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	void updateSupplierTest() {
		Supplier supplier = new Supplier(1, "update", "12345");
		service.update(supplier);
		assertEquals(1, supplier.getId());
		assertEquals("update", supplier.getName());
	}

	@Test
	@DisplayName("Teste apagar fornecedor")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	void deleteByIdTest() {
		service.delete(1);
		List<Supplier> list = service.listAll();
		assertEquals(2, list.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("O fornecedor 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar fornecedor por nome")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	void findByNameTest() {
		var lista = service.findByNameStartingWithIgnoreCaseOrderByNameDesc("forn");
		assertEquals(3, lista.size());
		var exception = assertThrows(ObjectNotFound.class,
				() -> service.findByNameStartingWithIgnoreCaseOrderByNameDesc("fornicedor"));
		assertEquals("Nenhum fornecedor cadastrado com o nome fornicedor", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar fornecedor por CNPJ")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	void findByCnpjTest() {
		var lista = service.findByCnpj("123456789");
		assertEquals(1, lista.get().getId());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByCnpj("32165"));
		assertEquals("Fornecedor não encontrado com o CNPJ 32165", exception.getMessage());
	}
}
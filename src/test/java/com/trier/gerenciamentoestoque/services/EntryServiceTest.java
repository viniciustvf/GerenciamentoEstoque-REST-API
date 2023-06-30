package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Entry;
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
public class EntryServiceTest extends BaseTests {

	@Autowired
	EntryService service;

	@Autowired
	SupplierService supplierService;

	@Test
	@DisplayName("Teste buscar entrada por ID")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	void findByIdTest() {
		var entryNull = service.findById(0);
		assertNull(entryNull);
		var entry = service.findById(1);
		assertNotNull(entry);
		assertEquals(1, entry.getId());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("A entrada 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir entrada")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	void insertEntryTest() {
		Entry entry = new Entry(null, 1, supplierService.findById(1));
		service.insert(entry);
		entry = service.findById(entry.getId());
		assertEquals(1, entry.getCodStock());
		assertEquals(1, entry.getSupplier().getId());
		Entry entry2 = new Entry(null, null, supplierService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(entry2));
		assertEquals("Código do estoque não pode ser nulo", exception.getMessage());
		Entry entry3 = new Entry(null, 1, null);
		var exceptio2 = assertThrows(IntegrityViolation.class, () -> service.insert(entry3));
		assertEquals("Fornecedor não pode ser nulo", exceptio2.getMessage());

	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	void listAllTest() {
		List<Entry> lista = service.listAll();
		assertEquals(3, lista.size());
	}

	@Test
	@DisplayName("Teste listar todos sem entradas cadastradas")
	void listAllWrongTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhuma entrada cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar entrada")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	void updateEntryTest() {
		Entry entry = new Entry(1, 3, supplierService.findById(1));
		service.update(entry);
		assertEquals(1, entry.getId());
		assertEquals(3, entry.getCodStock());
	}

	@Test
	@DisplayName("Teste apagar entrada")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	void deleteByIdTest() {
		service.delete(1);
		List<Entry> list = service.listAll();
		assertEquals(2, list.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("A entrada 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar entrada contendo nome")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	void findByCodStockTest() {
		var lista = service.findByCodStock(1);
		assertEquals(1, lista.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByCodStock(6));
		assertEquals("Nenhum estoque encontrado com código 6", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar entrada contendo nome")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	void findBySupplierTest() {
		var lista = service.findBySupplier(supplierService.findById(1));
		assertEquals(1, lista.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findBySupplier(supplierService.findById(3)));
		assertEquals("Nenhum fornecedor 3 encontrado", exception.getMessage());
	}
}
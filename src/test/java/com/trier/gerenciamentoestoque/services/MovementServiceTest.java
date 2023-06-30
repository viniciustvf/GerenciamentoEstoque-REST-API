package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Supplier;
import com.trier.gerenciamentoestoque.models.enums.MovementType;

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
public class MovementServiceTest extends BaseTests {

	@Autowired
	MovementService service;

	@Autowired
	EntryService entryService;

	@Autowired
	OutputService outputService;

	@Test
	@DisplayName("Teste buscar movimento por ID")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	void findByIdTest() {
		var movement = service.findById(1);
		assertNotNull(movement);
		assertEquals(1, movement.getId());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("O movimento 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir movimento")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	void insertMovementTest() {
		ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.of(2025, 1, 1, 20, 30, 5),
				ZoneId.of("America/Sao_Paulo"));

		Movement movement = new Movement(null, dateTime, entryService.findById(1), null);
		service.insert(movement);
		movement = service.findById(movement.getId());
		assertEquals("ENTRY", movement.getMovementType().name());
		assertEquals(20, movement.getDateTime().getHour());

		Movement movement2 = new Movement(null, null, null, outputService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(movement2));
		assertEquals("Data e hora inválidas", exception.getMessage());

		Movement movement3 = new Movement(null, dateTime, null, outputService.findById(1));
		service.insert(movement3);
		assertEquals("OUTPUT", movement3.getMovementType().name());

		Movement movement4 = new Movement(null, dateTime, entryService.findById(1), null);
		service.insert(movement4);
		assertEquals("ENTRY", movement4.getMovementType().name());

		Movement movement5 = new Movement(null, dateTime, entryService.findById(1), outputService.findById(1));
		var exception2 = assertThrows(IntegrityViolation.class, () -> service.insert(movement5));
		assertEquals("O produto não pode ter uma entrada e uma saída", exception2.getMessage());

		ZonedDateTime dateTime2 = ZonedDateTime.of(LocalDateTime.of(2021, 1, 1, 20, 30, 5),
				ZoneId.of("America/Sao_Paulo"));
		Movement movement6 = new Movement(null, dateTime2, entryService.findById(1), null);
		var exception3 = assertThrows(IntegrityViolation.class, () -> service.insert(movement6));
		assertEquals("Data e hora inválidas", exception3.getMessage());

	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	void listAllTest() {
		List<Movement> lista = service.listAll();
		assertEquals(3, lista.size());
	}

	@Test
	@DisplayName("Teste listar todos sem moviemntos cadastrados")
	void listAllWrongTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum movimento cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar movimento")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	void updateMovementTest() {
		ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.of(2026, 1, 1, 20, 30, 5),
				ZoneId.of("America/Sao_Paulo"));
		Movement movement = new Movement(1, dateTime, entryService.findById(1), null);
		service.update(movement);
		movement = service.findById(movement.getId());
		assertEquals("ENTRY", movement.getMovementType().name());
		assertEquals(2026, movement.getDateTime().getYear());
	}

	@Test
	@DisplayName("Teste apagar movimento")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	void deleteByIdTest() {
		service.delete(1);
		List<Movement> list = service.listAll();
		assertEquals(2, list.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("O movimento 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar por data e hora")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	void findByDateTimeTest() {
		var lista = service.findByDateTime(
				ZonedDateTime.of(LocalDateTime.of(2024, 1, 1, 20, 30, 5), ZoneId.of("America/Sao_Paulo")));
		assertEquals(1, lista.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByDateTime(
				ZonedDateTime.of(LocalDateTime.of(2028, 1, 1, 20, 30, 5), ZoneId.of("America/Sao_Paulo"))));
		assertEquals("Nenhum movimento encontrado na data 2028-01-01T20:30:05-03:00[America/Sao_Paulo]",
				exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar por entrada")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	void findByEntryTest() {
		var lista = service.findByEntry(entryService.findById(1));
		assertEquals(1, lista.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByEntry(entryService.findById(2)));
		assertEquals("Nenhum movimento de entrada encontrado para a entrada 2", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar por saída")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	void findByOutputTest() {
		var lista = service.findByOutput(outputService.findById(1));
		assertEquals(2, lista.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByOutput(outputService.findById(2)));
		assertEquals("Nenhum movimento de saída encontrado para a saída 2", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar por data e hora, contendo data")
	@Sql({ "classpath:/resources/sqls/supplier.sql" })
	@Sql({ "classpath:/resources/sqls/entry.sql" })
	@Sql({ "classpath:/resources/sqls/client.sql" })
	@Sql({ "classpath:/resources/sqls/seller.sql" })
	@Sql({ "classpath:/resources/sqls/output.sql" })
	@Sql({ "classpath:/resources/sqls/movement.sql" })
	void findByDateTimeContainsCurrentDateTest() {
		var lista = service.findByDateTimeContainsDate(
				ZonedDateTime.of(LocalDateTime.of(2024, 1, 1, 13, 59, 5), ZoneId.of("America/Sao_Paulo")));
		assertEquals(1, lista.size());
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByDateTimeContainsDate(
				ZonedDateTime.of(LocalDateTime.of(2029, 1, 1, 13, 59, 5), ZoneId.of("America/Sao_Paulo"))));
		assertEquals("Nenhum movimento encontrado para a data 2029-01-01T00:00-03:00[America/Sao_Paulo]",
				exception.getMessage());
	}

}
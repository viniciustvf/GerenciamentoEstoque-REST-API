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
	
	@Test
    @DisplayName("Teste buscar movimento por ID")
	@Sql({"classpath:/resources/sqls/supplier.sql"})
	@Sql({"classpath:/resources/sqls/entry.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/seller.sql"})
	@Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
    @Sql({"classpath:/resources/sqls/movement_type.sql"})
    void findByIdTest() {
        var movement = service.findById(1);
        assertNotNull(movement);
        assertEquals(1, movement.getId());
        var exception = assertThrows(ObjectNotFound.class,() -> service.findById(10));
        assertEquals("O movimento 10 não existe", exception.getMessage());
    }
    
    
    @Test
    @DisplayName("Teste inserir movimento")
	@Sql({"classpath:/resources/sqls/supplier.sql"})
	@Sql({"classpath:/resources/sqls/entry.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	@Sql({"classpath:/resources/sqls/seller.sql"})
	@Sql({"classpath:/resources/sqls/output.sql"})
    void insertMovementTest() {
    	ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.of(2012, 1, 1, 20, 30, 5), ZoneId.of("America/Sao_Paulo"));
    	Movement movement = new Movement(null,  dateTime, MovementType.ENTRY, entryService.findById(1), null);
        service.insert(movement);
        movement = service.findById(movement.getId());
        assertEquals("Entry", movement.getMovementType().getDescription());
        assertEquals(20, movement.getDateTime().getHour());
        Movement movement2 = new Movement(null, null, supplierService.findById(1));
        var exception = assertThrows(IntegrityViolation.class,() -> service.insert(movement2));
        assertEquals("Código do estoque não pode ser nulo", exception.getMessage());
        Movement movement3 = new Movement(null, 1, null);
        var exceptio2 = assertThrows(IntegrityViolation.class,() -> service.insert(movement3));
        assertEquals("Fornecedor não pode ser nulo", exceptio2.getMessage());

    }
    
	
}
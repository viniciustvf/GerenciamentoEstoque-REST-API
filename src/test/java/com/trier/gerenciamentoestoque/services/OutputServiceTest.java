package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.Output;
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
public class OutputServiceTest extends BaseTests {
	
	@Autowired
	OutputService service;
	
	@Autowired
	SellerService sellerService;
	
	@Autowired
	ClientService clientService;
	
	@Test
    @DisplayName("Teste buscar saida por ID")
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
	@Sql({"classpath:/resources/sqls/output.sql"})
    void findByIdTest() {
		var outputNull = service.findById(0);
		assertNull(outputNull);
		var output = service.findById(1);
        assertNotNull(output);
        assertEquals(1, output.getId());
        var exception = assertThrows(ObjectNotFound.class,() -> service.findById(10));
        assertEquals("A saída 10 não existe", exception.getMessage());
    }
    
    
    @Test
    @DisplayName("Teste inserir saída")
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    void insertOutputTest() {
    	Output output = new Output(null, clientService.findById(1), sellerService.findById(1));
        service.insert(output);
        output = service.findById(output.getId());
        assertEquals(20, output.getClient().getAge());
        assertEquals(1, output.getSeller().getId());
        Output output2 = new Output(2, null, sellerService.findById(1));
        var exception = assertThrows(IntegrityViolation.class,() -> service.insert(output2));
        assertEquals("Cliente não pode ser nulo", exception.getMessage());
        Output output3 = new Output(3, clientService.findById(1), null); 
        var exceptio3 = assertThrows(IntegrityViolation.class,() -> service.insert(output3));
        assertEquals("Vendedor não pode ser nulo", exceptio3.getMessage());

    }
    
    @Test
    @DisplayName("Teste listar todos")
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
	@Sql({"classpath:/resources/sqls/output.sql"})
    void listAllTest() {
    	List<Output> lista = service.listAll();
    	assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Teste listar todos sem saidas cadastradas")
    void listAllWrongTest() {
    	var exception = assertThrows(ObjectNotFound.class,() -> service.listAll());
        assertEquals("Nenhuma saída cadastrada", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste alterar saida")
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
	@Sql({"classpath:/resources/sqls/output.sql"})
    void updateOutputTest() {
    	Output output = new Output(1, clientService.findById(2), sellerService.findById(2));
    	service.update(output);
        assertEquals(1, output.getId());
        assertEquals(2, output.getClient().getId());
        assertEquals(2, output.getSeller().getId());
    }
    
    @Test
    @DisplayName("Teste apagar saida")
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
	@Sql({"classpath:/resources/sqls/output.sql"})
    void deleteByIdTest() {
    	service.delete(1);
        List<Output> list = service.listAll();
        assertEquals(2, list.size());
        var exception = assertThrows(ObjectNotFound.class,() -> service.delete(10));
        assertEquals("A saída 10 não existe", exception.getMessage()); 
    }
    
    @Test
    @DisplayName("Teste buscar saida por cliente")
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
	@Sql({"classpath:/resources/sqls/output.sql"})
    void findByClientTest() {
        var lista = service.findByClient(clientService.findById(1));
        assertEquals(1, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByClient(clientService.findById(3)));
        assertEquals("Nenhuma saída encontrada para o cliente Nome 3", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar saida por vendedor")
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
	@Sql({"classpath:/resources/sqls/output.sql"})
    void findBySellerTest() {
        var lista = service.findBySeller(sellerService.findById(1));
        assertEquals(1, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findBySeller(sellerService.findById(3)));
        assertEquals("Nenhuma saída encontrada para o vendedor Vendedor 3 (123455)", exception.getMessage());
    }
  
}
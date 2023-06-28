package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.Seller;
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
public class SellerServiceTest extends BaseTests {
	
	@Autowired
	SellerService service;
	
	
	@Test
    @DisplayName("Teste buscar vendedor por ID")
	@Sql({"classpath:/resources/sqls/seller.sql"})
    void findByIdTest() {
        var seller = service.findById(1);
        assertNotNull(seller);
        assertEquals(1, seller.getId());
        var exception = assertThrows(ObjectNotFound.class,() -> service.findById(10));
        assertEquals("O vendedor 10 não existe", exception.getMessage());
    }
    
    
    @Test
    @DisplayName("Teste inserir vendedor")
    void insertSellerTest() {
    	Seller seller = new Seller(null, "insert", "12345");
        service.insert(seller);
        seller = service.findById(seller.getId());
        assertEquals("insert", seller.getName());
        assertEquals("12345", seller.getRegistration());
        
        Seller seller2 = new Seller(null, null, "123456");
        var exception = assertThrows(IntegrityViolation.class,() -> service.insert(seller2));
        assertEquals("O nome do vendedor(a) não pode ser nulo", exception.getMessage());
        
        Seller seller3 = new Seller(null, "insert2", null);
        var exceptio2 = assertThrows(IntegrityViolation.class,() -> service.insert(seller3));
        assertEquals("A matricula do vendedor náo pode ser nula", exceptio2.getMessage());
    }
    
    @Test
    @DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/seller.sql"})
    void listAllTest() {
    	List<Seller> lista = service.listAll();
    	assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Teste listar todos sem vendedores cadastrados")
    void listAllWrongTest() {
    	var exception = assertThrows(ObjectNotFound.class,() -> service.listAll());
        assertEquals("Nenhum vendedor cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste alterar vendedor")
	@Sql({"classpath:/resources/sqls/seller.sql"})
    void updateSellerTest() {
    	Seller seller = new Seller(1, "update", "12345");
    	service.update(seller);
        assertEquals(1, seller.getId());
        assertEquals("update", seller.getName());
    }
    
    @Test
    @DisplayName("Teste apagar vendedor")
	@Sql({"classpath:/resources/sqls/seller.sql"})
    void deleteByIdTest() {
    	service.delete(1);
        List<Seller> list = service.listAll();
        assertEquals(2, list.size());
        var exception = assertThrows(ObjectNotFound.class,() -> service.delete(10));
        assertEquals("O vendedor 10 não existe", exception.getMessage()); 
    }
    
    @Test
    @DisplayName("Teste buscar vendedor por nome")
	@Sql({"classpath:/resources/sqls/seller.sql"})
    void findByNameTest() {
        var lista = service.findByNameStartingWithIgnoreCaseOrderByNameDesc("vende");
        assertEquals(3, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameStartingWithIgnoreCaseOrderByNameDesc("zendedor"));
        assertEquals("Nenhum vendedor com nome zendedor cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar vendedor matricula")
	@Sql({"classpath:/resources/sqls/seller.sql"})
    void findByRegistrationTest() {
        var lista = service.findByRegistration("12345");
        assertEquals(1, lista.get().getId());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByRegistration("32165"));
        assertEquals("Vendedor não encontrado com a matrícula 32165", exception.getMessage());
    }
}
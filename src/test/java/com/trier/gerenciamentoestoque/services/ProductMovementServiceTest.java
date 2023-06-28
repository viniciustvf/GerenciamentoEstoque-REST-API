package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.ProductMovement;
import com.trier.gerenciamentoestoque.models.ProductMovement;
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
public class ProductMovementServiceTest extends BaseTests {
	
	@Autowired
	ProductMovementService service;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	MovementService movementService;
	
	@Test
    @DisplayName("Teste buscar saida por ID")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void findByIdTest() {
        var productMovement = service.findById(1);
        assertNotNull(productMovement);
        assertEquals(1, productMovement.getId());
        var exception = assertThrows(ObjectNotFound.class,() -> service.findById(10));
        assertEquals("O produto movimento 10 não existe", exception.getMessage());
    }
    
    
    @Test
    @DisplayName("Teste inserir produto movimento")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
    void insertProductMovementTest() {
    	ProductMovement productMovement = new ProductMovement(null,  2, productService.findById(1), movementService.findById(1));
        service.insert(productMovement);
        productMovement = service.findById(productMovement.getId());
        assertEquals(7, productMovement.getProduct().getAmount());
        
        ProductMovement productMovement2 = new ProductMovement(null,  2, productService.findById(2), movementService.findById(2));
        service.insert(productMovement2);
        productMovement = service.findById(productMovement.getId());
        assertEquals(4, productMovement2.getProduct().getAmount());
        
        ProductMovement productMovement3 = new ProductMovement(null,  2, null, movementService.findById(2));
        var exception = assertThrows(IntegrityViolation.class,() -> service.insert(productMovement3));
        assertEquals("Produto não pode ser nulo", exception.getMessage());
        
        ProductMovement productMovement4 = new ProductMovement(null,  2, productService.findById(1), null);
        var exception2 = assertThrows(IntegrityViolation.class,() -> service.insert(productMovement4));
        assertEquals("Movimento não pode ser nulo", exception2.getMessage());
        
        ProductMovement productMovement5 = new ProductMovement(null,  null, productService.findById(1), movementService.findById(2));
        var exception3 = assertThrows(IntegrityViolation.class,() -> service.insert(productMovement5));
        assertEquals("Quantidade não pode ser nula e maior que 100", exception3.getMessage());
        
        ProductMovement productMovement6 = new ProductMovement(null,  101, productService.findById(1), movementService.findById(2));
        var exception4 = assertThrows(IntegrityViolation.class,() -> service.insert(productMovement6));
        assertEquals("Quantidade não pode ser nula e maior que 100", exception4.getMessage());
    }
    
    @Test
    @DisplayName("Teste listar todos")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void listAllTest() {
    	List<ProductMovement> lista = service.listAll();
    	assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Teste listar todos sem produto movimentados cadastrados")
    void listAllWrongTest() {
    	var exception = assertThrows(ObjectNotFound.class,() -> service.listAll());
        assertEquals("Nenhum produto movimento cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste alterar produto movimento")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void updateProductMovementTest() {
    	ProductMovement productMovement = new ProductMovement(1,  2, productService.findById(2), movementService.findById(2));
    	service.update(productMovement);
        assertEquals(1, productMovement.getId());
        assertEquals(2, productMovement.getProduct().getId());
        assertEquals(2, productMovement.getMovement().getId());
    }
    
    @Test
    @DisplayName("Teste apagar produto movimento")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void deleteByIdTest() {
    	service.delete(1);
        List<ProductMovement> list = service.listAll();
        assertEquals(1, list.size());
        var exception = assertThrows(ObjectNotFound.class,() -> service.delete(10));
        assertEquals("O produto movimento 10 não existe", exception.getMessage()); 
    }
    
    @Test
    @DisplayName("Teste buscar produto movimentado por quantidade")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void findByQuantityTest() {
        var lista = service.findByQuantity(2);
        assertEquals(2, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByQuantity(6));
        assertEquals("Nenhum produto movimento encontrado com a quantidade de 6 produtos", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar produto movimentado por produto")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void findByProductTest() {
        var lista = service.findByProduct(productService.findById(1));
        assertEquals(1, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByProduct(productService.findById(3)));
        assertEquals("Nenhum produto movimento encontrado para o produto Produto 3", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar produto movimentado por movimento")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void findByMovementTest() {
        var lista = service.findByMovement(movementService.findById(1));
        assertEquals(1, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByMovement(movementService.findById(3)));
        assertEquals("Nenhum movimento com id 3 encontrado", exception.getMessage());
    }
}
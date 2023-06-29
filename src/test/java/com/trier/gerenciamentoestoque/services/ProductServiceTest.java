package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.Product;
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
public class ProductServiceTest extends BaseTests {
	
	@Autowired
	ProductService service;
	
	@Autowired
	CategoryService categoryService;
	
	
	@Test
    @DisplayName("Teste buscar produto por ID")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByIdTest() {
        var product = service.findById(1);
        assertNotNull(product);
        assertEquals(1, product.getId());
        var exception = assertThrows(ObjectNotFound.class,() -> service.findById(10));
        assertEquals("O produto 10 não existe", exception.getMessage());
    }
    
    
    @Test
    @DisplayName("Teste inserir produto")
    @Sql({"classpath:/resources/sqls/category.sql"})
    void insertProductTest() {
    	Product product = new Product(null, "insert", 20.0, 12345, 2, categoryService.findById(1));
        service.insert(product);
        product = service.findById(product.getId());
        assertEquals(20.0, product.getPrice());
        
    	Product product2 = new Product(null, null, 20.0, 12345, 2, categoryService.findById(1));
    	var exception = assertThrows(IntegrityViolation.class,() -> service.insert(product2));
        assertEquals("O nome não pode ser nulo", exception.getMessage());
        
        Product product3 = new Product(null, "insert", null, 12345, 2, categoryService.findById(1));
    	var exceptio3 = assertThrows(IntegrityViolation.class,() -> service.insert(product3));
        assertEquals("O preço não pode ser nulo", exceptio3.getMessage());
        
        Product product4 = new Product(null, "insert", 20.0, null, 2, categoryService.findById(1));
    	var exception4 = assertThrows(IntegrityViolation.class,() -> service.insert(product4));
        assertEquals("O codigo de barras não pode ser nulo", exception4.getMessage());
        
        Product product5 = new Product(null, "insert", 20.0, 12345, null, categoryService.findById(1));
    	var exception5 = assertThrows(IntegrityViolation.class,() -> service.insert(product5));
        assertEquals("A quantidade não pode ser nula", exception5.getMessage());
        
        Product product6 = new Product(null, "insert", 20.0, 12345, 2, null);
    	var exception6 = assertThrows(IntegrityViolation.class,() -> service.insert(product6));
        assertEquals("A categoria não pode ser nula", exception6.getMessage());
    }
    
    @Test
    @DisplayName("Teste listar todos")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void listAllTest() {
    	List<Product> lista = service.listAll();
    	assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Teste listar todos sem produtos cadastrados")
    void listAllWrongTest() {
    	var exception = assertThrows(ObjectNotFound.class,() -> service.listAll());
        assertEquals("Nenhum produto cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste alterar produto")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void updateProductTest() {
    	Product product = new Product(1, "update", 20.0, 12345, 2, categoryService.findById(1));
    	service.update(product);
        assertEquals(1, product.getId());
        assertEquals("update", product.getName());
    }
    
    @Test
    @DisplayName("Teste apagar produto")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void deleteByIdTest() {
    	service.delete(1);
        List<Product> list = service.listAll();
        assertEquals(2, list.size());
        var exception = assertThrows(ObjectNotFound.class,() -> service.delete(10));
        assertEquals("O produto 10 não existe", exception.getMessage()); 
    }
    
    @Test
    @DisplayName("Teste buscar produto por nome contendo")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByNameTest() {
        var lista = service.findByNameStartingWithIgnoreCaseOrderByNameDesc("prod");
        assertEquals(3, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameStartingWithIgnoreCaseOrderByNameDesc("prodi"));
        assertEquals("Nenhum produto encontrado com o nome prodi", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar produto por preço")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByQuantityTest() {
        var lista = service.findByPrice(200.0);
        assertEquals(1, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByPrice(500.0));
        assertEquals("Nenhum produto encontrado com o preço 500.0", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar produto por preço entre")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByPriceBetweenTest() {
        var lista = service.findByPriceBetween(1.0, 500.0);
        assertEquals(3, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByPriceBetween(500.0, 600.0));
        assertEquals("Nenhum produto encontrado entre R$500.0 e R$600.0", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar produto por codigo de barras")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByBarcodeTest() {
        var lista = service.findByBarcode(1421500);
        assertEquals("Produto 1", lista.get().getName());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByBarcode(3699600));
        assertEquals("Produto não encontrado com código de barras 3699600", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar produto por quantidade em estoque")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByAmountTest() {
        var lista = service.findByAmount(6);
        assertEquals(2, lista.size());
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByAmount(10));
        assertEquals("Nenhum produto encontrado com estoque de 10", exception.getMessage());
    }

    @Test
    @DisplayName("Teste buscar produto entre quantidades em estoque")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByAmountBetweenTest() {
    	var lista = service.findByAmountBetween(0, 10);
    	assertEquals(3, lista.size());
    	var exception = assertThrows(ObjectNotFound.class, () -> service.findByAmountBetween(10, 15));
    	assertEquals("Nenhum produto encontrado com estoque entre 10 e 15", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar produto por categoria")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByCategoryTest() {
    	var lista = service.findByCategory(categoryService.findById(1));
    	assertEquals(1, lista.size());
    	var exception = assertThrows(ObjectNotFound.class, () -> service.findByCategory(categoryService.findById(3)));
    	assertEquals("Nenhum produto encontrado para a categoria Categoria 3", exception.getMessage());
    } 
    
    @Test
    @DisplayName("Teste buscar valor total dos produtos")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    void findByTotalValueTest() {
    	var lista = service.findTotalValueOfProducts();
    	assertEquals(3094.0, lista);;
    } 
    
    @Test
    @DisplayName("Teste buscar valor total dos produtos sem produtos cadastrados")
    @Sql({"classpath:/resources/sqls/category.sql"})
    void findByTotalValueWrongTest() {
    	var exception = assertThrows(ObjectNotFound.class, () -> service.findTotalValueOfProducts());
    	assertEquals("Nenhum produto encontrado", exception.getMessage());
    }
    
}
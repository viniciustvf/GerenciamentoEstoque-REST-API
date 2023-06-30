package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.trier.gerenciamentoestoque.BaseTests;
import com.trier.gerenciamentoestoque.models.dto.ClientsOfSellerDTO;
import com.trier.gerenciamentoestoque.models.dto.ProductMovementDateDTO;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class ReportServiceTest extends BaseTests {
	
	@Autowired
	ReportService service;
	
	
	@Test
    @DisplayName("Teste buscar produtos movimentados por data")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void findProductByDateTest() {
        ProductMovementDateDTO pm = service.findProductByDate("22-06-2026");
        assertNotNull(pm);
        assertEquals(0, pm.getProducts().size());
        ProductMovementDateDTO pm2 = service.findProductByDate("01-01-2024");
        assertNotNull(pm2);
        assertEquals(1, pm2.getProductSize());
    }
    
	@Test
    @DisplayName("Teste buscar os clientes de um vendedor")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void findClientsOfSellerTest() {
		ClientsOfSellerDTO cs = service.findClientsOfSeller(1);
        assertNotNull(cs);
        assertEquals(1, cs.getClientsSize());
    }
	
	@Test
    @DisplayName("Teste buscar valor total dos produtos")
    @Sql({"classpath:/resources/sqls/category.sql"})
    @Sql({"classpath:/resources/sqls/product.sql"})
    @Sql({"classpath:/resources/sqls/supplier.sql"})
    @Sql({"classpath:/resources/sqls/entry.sql"})
    @Sql({"classpath:/resources/sqls/client.sql"})
    @Sql({"classpath:/resources/sqls/seller.sql"})
    @Sql({"classpath:/resources/sqls/output.sql"})
    @Sql({"classpath:/resources/sqls/movement.sql"})
	@Sql({"classpath:/resources/sqls/productMovement.sql"})
    void findTotalValueOfProductsTest() {
		Double value = service.findTotalValueOfProducts();
        assertNotNull(value);
        assertEquals(3094.0, value);
    }
    
    

}
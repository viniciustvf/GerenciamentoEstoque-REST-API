package com.trier.gerenciamentoestoque.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.models.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.trier.gerenciamentoestoque.BaseTests;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class CategoryServiceTest extends BaseTests {

	@Autowired
	CategoryService categoryService;

	@Test
	@DisplayName("Teste buscar categoria por ID")
	@Sql({ "classpath:/resources/sqls/category.sql" })
	void findByIdTest() {
		var category = categoryService.findById(1);
		assertNotNull(category);
		assertEquals(1, category.getId());
		assertEquals("Categoria 1", category.getDescription());
		var exception = assertThrows(ObjectNotFound.class, () -> categoryService.findById(10));
		assertEquals("A categoria 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir categoria")
	void insertCategoryTest() {
		Category category = new Category(null, "insert");
		categoryService.insert(category);
		category = categoryService.findById(category.getId());
		assertEquals("insert", category.getDescription());
		Category category2 = new Category(null, null);
		var exception = assertThrows(IntegrityViolation.class, () -> categoryService.insert(category2));
		assertEquals("Descrição da categoria não pode ser nula", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar categoria")
	@Sql({ "classpath:/resources/sqls/category.sql" })
	void updateCategoryTest() {
		Category category = new Category(1, "update");
		categoryService.update(category);
		assertEquals(1, category.getId());
		assertEquals("update", category.getDescription());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/category.sql" })
	void listAllTest() {
		List<Category> lista = categoryService.listAll();
		assertEquals(3, lista.size());
	}

	@Test
	@DisplayName("Teste listar todos sem categoria cadastrada")
	void listAllWrongTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> categoryService.listAll());
		assertEquals("Nenhuma categoria cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste apagar categoria")
	@Sql({ "classpath:/resources/sqls/category.sql" })
	void deleteByIdTest() {
		categoryService.delete(1);
		List<Category> list = categoryService.listAll();
		assertEquals(2, list.size());
		var exception = assertThrows(ObjectNotFound.class, () -> categoryService.delete(10));
		assertEquals("A categoria 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar categoria por descrição com ignore case")
	@Sql({ "classpath:/resources/sqls/category.sql" })
	void findByDescriptionIgnoreCaseWrongTest() {
		var lista = categoryService.findByDescriptionStartingWithIgnoreCase("c");
		assertEquals(3, lista.size());
		var exception = assertThrows(ObjectNotFound.class,
				() -> categoryService.findByDescriptionStartingWithIgnoreCase("z"));
		assertEquals("Nenhuma categoria cadastrada com a descrição z", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar categoria contendo descrição")
	@Sql({ "classpath:/resources/sqls/category.sql" })
	void findByDescriptionContainingOrderTest() {
		var lista = categoryService.findByDescriptionContainingOrderByDescriptionDesc("cat");
		assertEquals(3, lista.size());
		var exception = assertThrows(ObjectNotFound.class,
				() -> categoryService.findByDescriptionContainingOrderByDescriptionDesc("kateg"));
		assertEquals("Nenhuma categoria cadastrada com a descrição kateg", exception.getMessage());
	}

}
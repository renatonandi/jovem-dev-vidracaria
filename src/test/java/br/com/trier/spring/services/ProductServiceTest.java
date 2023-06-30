package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.Type;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class ProductServiceTest extends BaseTests{
	
	@Autowired
	ProductService service;
	
	@Autowired
	TypeService typeService;
	
	@Test
	@DisplayName("Teste buscar produto por ID")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void findByIdTest() {
		assertNotNull(service.findById(1));
		assertEquals("Espelho", service.findById(1).getName());
		assertEquals(35, service.findById(1).getValue());
		assertEquals("Bronze", service.findById(1).getType().getDescription());
	}

	@Test
	@DisplayName("Teste buscar tipo por ID inexistente")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void findByIdInvalidTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
		assertEquals("O produto 4 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir produto")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void insertProductTest() {
		Product insert = new Product(null, "Espelho", 35.0, typeService.findById(1));
		service.insert(insert);
		var search = service.findById(1);
		assertEquals(1, search.getId());
		assertEquals("Espelho", search.getName());
		assertEquals(35.0, search.getValue());
		assertEquals("Bronze", search.getType().getDescription());

	}

	@Test
	@DisplayName("Teste alterar produto")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void updateProductByIdTest() {
		var busca = service.findById(1);
		assertEquals("Espelho", busca.getName());
		Product altered = new Product(1, "Incolor",35.0, typeService.findById(1));
		service.update(altered);
		altered = service.findById(1);
		assertEquals("Incolor", altered.getName());
	}

	@Test
	@DisplayName("Teste alterar produto inexistente")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void updateProductByIdNonExistsTest() {
		Product altered = new Product(1, "Incolor",35.0, typeService.findById(1));
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(altered));
		assertEquals("O produto 1 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste apagar produto por id")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void deleteProductByIdTest() {
		service.delete(1);
		var list = service.listAll();
		assertEquals(2, list.size());
	}

	@Test
	@DisplayName("Teste apagar produto por id incorreto")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void deleteProductByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
		assertEquals("O produto 4 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de listagem de todos os registros")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void listAllTest() {
		assertNotNull(service.listAll());
		assertEquals(3, service.listAll().size());
	}

	@Test
	@DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum produto cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de busca de produto por nome contendo")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void findByNameTest() {
		var list = service.findByNameContainingIgnoreCase("esp");
		assertEquals(2, list.size());
		assertEquals("Espelho", list.get(0).getName());
	}

	@Test
	@DisplayName("Teste de busca de produto por nome errado")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void findByNameWrongTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameContainingIgnoreCase("w"));
		assertEquals("Nenhum produto encontrado para esse nome w", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste de busca de produto por tipo")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void findByTypeTest() {
		var list = service.findByType(typeService.findById(2));
		assertEquals(2, list.size());
		assertEquals("Espelho", list.get(0).getName());
		assertEquals("Incolor", list.get(1).getName());
	}
	
	@Test
	@DisplayName("Teste de busca de produto por tipo errado")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/product.sql" })
	void findByTypeWrongTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByType(typeService.findById(3)));
		assertEquals("Nenhum produto encontrado para esse tipo Fume", exception.getMessage());
	}

}

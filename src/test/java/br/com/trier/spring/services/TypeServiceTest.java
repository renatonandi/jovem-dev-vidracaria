package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.models.Request;
import br.com.trier.spring.models.Type;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class TypeServiceTest extends BaseTests {

	@Autowired
	TypeService service;

	@Test
	@DisplayName("Teste buscar tipo por ID")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void findByIdTest() {
		assertNotNull(service.findById(1));
		assertEquals("Bronze", service.findById(1).getDescription());
	}

	@Test
	@DisplayName("Teste buscar tipo por ID inexistente")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void findByIdInvalidTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
		assertEquals("O tipo 4 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir tipo")
	void insertTypeTest() {
		Type insert = new Type(null, "Bronze");
		service.insert(insert);
		var search = service.findById(1);
		assertEquals(1, search.getId());
		assertEquals("Bronze", search.getDescription());

	}

	@Test
	@DisplayName("Teste inserir tipo com descrição duplicada")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void insertTypeDuplicatedDescriptionTest() {
		Type insert = new Type(null, "bronze");
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(insert));
		assertEquals("Esse tipo já está cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar tipo")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void updateTypeByIdTest() {
		var busca = service.findById(1);
		assertEquals("Bronze", busca.getDescription());
		Type altered = new Type(1, "Incolor");
		service.update(altered);
		altered = service.findById(1);
		assertEquals("Incolor", altered.getDescription());
	}

	@Test
	@DisplayName("Teste alterar tipo inexistente")
	void updateTypeByIdNonExistsTest() {
		Type altered = new Type(1, "Uma porta de espelho fume");
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(altered));
		assertEquals("O tipo 1 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste apagar tipo por id")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void deleteTypeByIdTest() {
		service.delete(1);
		var list = service.listAll();
		assertEquals(2, list.size());
	}

	@Test
	@DisplayName("Teste apagar tipo por id incorreto")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void deleteTypeByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
		assertEquals("O tipo 4 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de listagem de todos os registros")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void listAllTest() {
		assertNotNull(service.listAll());
		assertEquals(3, service.listAll().size());
	}

	@Test
	@DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum tipo cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de busca de tipo por descrição contendo")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void findByDescriptionTest() {
		var list = service.findByDescriptionContainingIgnoreCase("bronze");
		assertEquals("Bronze", list.get(0).getDescription());
	}

	@Test
	@DisplayName("Teste de busca de tipo por descrição errada")
	@Sql({ "classpath:/resources/sqls/type.sql" })
	void findByDescriptionWrongTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByDescriptionContainingIgnoreCase("w"));
		assertEquals("Nenhum tipo encontrado para essa descrição w", exception.getMessage());
	}

}

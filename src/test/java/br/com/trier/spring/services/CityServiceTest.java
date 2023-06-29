package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.models.City;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class CityServiceTest extends BaseTests {

    @Autowired
    CityService service;

    @Test
    @DisplayName("Teste buscar cidade por ID")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void findByIdTest() {
        assertNotNull(service.findById(1));
        assertEquals("Tubarão", service.findById(1).getName());
        assertEquals("SC", service.findById(1).getUf());
    }

    @Test
    @DisplayName("Teste buscar cidade por ID inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void findByIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
        assertEquals("A cidade 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir cidade")
    void insertCityTest() {
        City city = new City(null, "Tubarão", "SC");
        service.insert(city);
        var search = service.findById(1);
        assertEquals(1, search.getId());
        assertEquals("Tubarão", search.getName());
        assertEquals("SC", search.getUf());
    }

    @Test
    @DisplayName("Teste inserir cidade com nome duplicado")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void insertCityDuplicatedNameTest() {
        City city = new City(4, "Tubarão", "SC");
        var exception = assertThrows(IntegrityViolation.class, () -> service.insert(city));
        assertEquals("Essa cidade já está cadastrada", exception.getMessage());
    }

    @Test
    @DisplayName("Teste alterar cidade")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void updateCityByIdTest() {
        var city = service.findById(1);
        assertEquals("Tubarão", city.getName());
        City alteredCity = new City(1, "CidadeAlterada", "SC");
        service.update(alteredCity);
        alteredCity = service.findById(1);
        assertEquals("CidadeAlterada", alteredCity.getName());
    }

    @Test
    @DisplayName("Teste alterar cidade inexistente")
    void updateCityByIdNonExistsTest() {

        City AlteredCity = new City(1, "CidadeAlterada", "SC");
        var exception = assertThrows(ObjectNotFound.class, () -> service.update(AlteredCity));
        assertEquals("A cidade 1 não existe", exception.getMessage());

    }

    @Test
    @DisplayName("Teste apagar cidade por id")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void deleteCityByIdTest() {
        service.delete(1);
        List<City> list = service.listAll();
        assertEquals(2, list.size());
        assertEquals("Laguna", list.get(0).getName());
    }

    @Test
    @DisplayName("Teste apagar cidade por id incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void deleteCityByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
        assertEquals("A cidade 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void listAllTest() {
        assertNotNull(service.listAll());
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
    void listAllNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhuma cidade cadastrada", exception.getMessage());
    }

    @Test
    @DisplayName("Teste buscar cidade por nome contendo")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void findByNameTest() {
        var city = service.findByNameContainingIgnoreCase("Tu");
        assertNotNull(city);
        assertEquals(1, city.size());
        assertEquals("Tubarão", city.get(0).getName());
    }

    @Test
    @DisplayName("Teste buscar cidade por nome errado")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void findByNameInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameContainingIgnoreCase("z"));
        assertEquals("Nenhuma cidade encontrada para esse nome z", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar cidade por nome contendo e UF")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void findByNameAndUfTest() {
        var city = service.findByNameContainingIgnoreCaseAndUfIgnoreCase("tu","sc");
        assertNotNull(city);
        assertEquals(1, city.size());
        assertEquals("Tubarão", city.get(0).getName());
    }

    @Test
    @DisplayName("Teste buscar cidade por nome contendo e UF incorreta")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void findByNameAndUfWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameContainingIgnoreCaseAndUfIgnoreCase("tu","US"));
        assertEquals("Nenhuma cidade encontrada para esse nome tu e para essa UF US", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste buscar cidade por UF")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void findByUfTest() {
        var city = service.findByUf("sc");
        assertNotNull(city);
        assertEquals(2, city.size());
        assertEquals("Tubarão", city.get(0).getName());
        assertEquals("Laguna", city.get(1).getName());
    }
    
    @Test
    @DisplayName("Teste buscar cidade por UF incorreta")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void findByUfWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByUf("US"));
        assertEquals("Nenhuma cidade encontrada para essa UF US", exception.getMessage());
    }


}

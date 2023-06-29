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
import br.com.trier.spring.models.Address;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class AddressServiceTest extends BaseTests{
    
    @Autowired
    AddressService service;
    
    @Autowired
    CityService cityService;
    
    @Test
    @DisplayName("Teste buscar endereço por ID")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void findByIdTest() {
        assertNotNull(service.findById(1));
        assertEquals("Laguna", service.findById(1).getStreet());
        assertEquals("Oficinas", service.findById(1).getNeighborhood());
        assertEquals("Tubarão", service.findById(1).getCity().getName());
    }

    @Test
    @DisplayName("Teste buscar endereco por ID inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void findByIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
        assertEquals("O endereço 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir endereco")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void insertAddressTest() {
        Address address = new Address(null, "Laguna", "Oficinas", cityService.findById(1));
        service.insert(address);
        var search = service.findById(1);
        assertEquals(1, search.getId());
        assertEquals("Laguna", search.getStreet());
        assertEquals("Oficinas", search.getNeighborhood());
        assertEquals("Tubarão", search.getCity().getName());
    }

    @Test
    @DisplayName("Teste alterar endereço")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void updateAddressByIdTest() {
        var address = service.findById(1);
        assertEquals("Laguna", address.getStreet());
        assertEquals("Oficinas", address.getNeighborhood());
        Address alteredAddress = new Address(1, "RuaAlterada", "BairroAlterado", cityService.findById(1));
        service.update(alteredAddress);
        alteredAddress = service.findById(1);
        assertEquals("RuaAlterada", alteredAddress.getStreet());
        assertEquals("BairroAlterado", alteredAddress.getNeighborhood());
    }

    @Test
    @DisplayName("Teste alterar endereço inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    void updateAddressByIdNonExistsTest() {
        Address AlteredAddress = new Address(1, "RuaAlterada", "BairroAlterado", cityService.findById(1));
        var exception = assertThrows(ObjectNotFound.class, () -> service.update(AlteredAddress));
        assertEquals("O endereço 1 não existe", exception.getMessage());

    }

    @Test
    @DisplayName("Teste apagar endereço por id")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void deleteAddressByIdTest() {
        service.delete(1);
        List<Address> list = service.listAll();
        assertEquals(2, list.size());
        assertEquals("RuaDeLaguna", list.get(0).getStreet());
    }

    @Test
    @DisplayName("Teste apagar endereço por id incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void deleteAddressByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
        assertEquals("O endereço 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void listAllTest() {
        assertNotNull(service.listAll());
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
    void listAllNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum endereço cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de endereço por rua")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void findByStreetTest() {
        var list = service.findByStreetContainingIgnoreCase("la");
        assertEquals(2, list.size());
        assertEquals("Laguna", list.get(0).getStreet());
        assertEquals("RuaDeLaguna", list.get(1).getStreet());
    }
    
    @Test
    @DisplayName("Teste de busca de de endereço por rua incorreta")
    void findByStreetWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByStreetContainingIgnoreCase("z"));
        assertEquals("Nenhum endereço encontrado para essa rua z", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de endereço por bairro")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void findByNeighborhoodTest() {
        var list = service.findByNeighborhoodContainingIgnoreCase("ofi");
        assertEquals(1, list.size());
        assertEquals("Laguna", list.get(0).getStreet());
        assertEquals("Tubarão", list.get(0).getCity().getName());
    }
    
    @Test
    @DisplayName("Teste de busca de endereço por bairro incorreta")
    void findByNeighborhoodWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNeighborhoodContainingIgnoreCase("z"));
        assertEquals("Nenhum endereço encontrado para esse bairro z", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de endereço por rua e bairro")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void findByStreetAndNeighborhoodTest() {
        var list = service.findByNeighborhoodContainingIgnoreCaseAndStreetContainingIgnoreCase("ba", "ru");
        assertEquals(2, list.size());
        assertEquals("RuaDeLaguna", list.get(0).getStreet());
        assertEquals("RuaAleatoria", list.get(1).getStreet());
    }
    
    @Test
    @DisplayName("Teste de busca de de endereço por rua e bairro incorreta")
    void findByStreetAndNeighborhoodWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNeighborhoodContainingIgnoreCaseAndStreetContainingIgnoreCase("z", "z"));
        assertEquals("Nenhum endereço encontrado para esse bairro z e para essa rua z", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de endereço por cidade")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void findByCityTest() {
        var list = service.findByCity(cityService.findById(1));
        assertEquals(1, list.size());
        assertEquals("Laguna", list.get(0).getStreet());
        assertEquals("Oficinas", list.get(0).getNeighborhood()); 
    }
    
    @Test
    @DisplayName("Teste de busca de endereço por cidade incorreta")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    void findByCityNonExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByCity(cityService.findById(3)));
        assertEquals("Nenhum endereço encontrado para essa cidade São Paulo", exception.getMessage());
    }

}

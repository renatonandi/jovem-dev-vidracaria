package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.models.Customer;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class CustomerServiceTest extends BaseTests{
    
    @Autowired
    CustomerService service;
    
    @Autowired
    DiscountService discountService;
    
    @Autowired
    AddressService addressService;
    
    @Autowired
    CityService cityService;
    
    @Test
    @DisplayName("Teste buscar cliente por ID")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByIdTest() {
        assertNotNull(service.findById(1));
        assertEquals("Renato", service.findById(1).getName());
        assertEquals(5, service.findById(1).getDiscount().getDiscount());
        assertEquals("Tubarão", service.findById(1).getAddress().getCity().getName());
    }

    @Test
    @DisplayName("Teste buscar cliente por ID inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
        assertEquals("Cliente 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir cliente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void insertCustomerTest() {
        Customer customer = new Customer(null, "Renato", addressService.findById(1), discountService.findById(1));
        service.insert(customer);
        var search = service.findById(1);
        assertEquals(1, search.getId());
        assertEquals("Renato", search.getName());
        assertEquals("Tubarão", search.getAddress().getCity().getName());
       
    }

    @Test
    @DisplayName("Teste alterar cliente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void updateCustomerByIdTest() {
        var busca = service.findById(1);
        assertEquals("Renato", busca.getName());
        Customer altered = new Customer(1, "Luis", addressService.findById(1), discountService.findById(1));
        service.update(altered);
        altered = service.findById(1);
        assertEquals("Luis", altered.getName());
    }

    @Test
    @DisplayName("Teste alterar cliente inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void updateCustomerByIdNonExistsTest() {
        Customer altered = new Customer(1, "Renato", addressService.findById(1), discountService.findById(1));
        var exception = assertThrows(ObjectNotFound.class, () -> service.update(altered));
        assertEquals("Cliente 1 não existe", exception.getMessage());

    }

    @Test
    @DisplayName("Teste apagar cliente por id")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void deleteCustomerByIdTest() {
        service.delete(1);
        var list = service.listAll();
        assertEquals(2, list.size());
        assertEquals("João", list.get(0).getName());
    }

    @Test
    @DisplayName("Teste apagar cliente por id incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void deleteCustomerByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
        assertEquals("Cliente 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void listAllTest() {
        assertNotNull(service.listAll());
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
    void listAllNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum cliente cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de clientes por nome contendo")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByNameTest() {
        var list = service.findByNameContainingIgnoreCase("re");
        assertEquals(1, list.size());
        assertEquals("Renato", list.get(0).getName());
        assertEquals("Tubarão", list.get(0).getAddress().getCity().getName());
    }

    @Test
    @DisplayName("Teste de busca de clientes por nome inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByNameNonExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameContainingIgnoreCase("w"));
        assertEquals("Nenhum cliente encontrado para esse nome w", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de clientes por endereço")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByAddressTest() {
        var list = service.findByAddress(addressService.findById(1));
        assertEquals(1, list.size());
        assertEquals("Renato", list.get(0).getName());
        assertEquals("Tubarão", list.get(0).getAddress().getCity().getName());
    }

    @Test
    @DisplayName("Teste de busca de clientes por endereço incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByAddressWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByAddress(addressService.findById(2)));
        assertEquals("Nenhum cliente encontrado para esse endereço: RuaDeLaguna , BairroDeLaguna e Laguna", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de busca de clientes por desconto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByDiscountTest() {
        var list = service.findByDiscount(discountService.findById(2));
        assertEquals(1, list.size());
        assertEquals("João", list.get(0).getName());
        assertEquals("Laguna", list.get(0).getAddress().getCity().getName());
    }

    @Test
    @DisplayName("Teste de busca de clientes por desconto incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByDiscountWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByDiscount(discountService.findById(3)));
        assertEquals("Nenhum cliente encontrado para esse desconto 15", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de clientes por nome e endereço")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByNameAndAddressTest() {
        var list = service.findByNameContainingIgnoreCaseAndAddress("re", addressService.findById(1));
        assertEquals(1, list.size());
        assertEquals("Renato", list.get(0).getName());
        assertEquals("Tubarão", list.get(0).getAddress().getCity().getName());
    }
    
    @Test
    @DisplayName("Teste de busca de clientes por nome e endereço incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByNameAndAddressWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameContainingIgnoreCaseAndAddress("re",addressService.findById(2)));
        assertEquals("Nenhum cliente encontrado para esse nome re e endereço rua RuaDeLaguna bairro BairroDeLaguna", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de busca de clientes por nome incorreto e endereço")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void findByNameWrongAndAddressTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameContainingIgnoreCaseAndAddress("z",addressService.findById(1)));
        assertEquals("Nenhum cliente encontrado para esse nome z e endereço rua Laguna bairro Oficinas", exception.getMessage());
    }

}

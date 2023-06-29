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
public class PhoneNumberServiceTest extends BaseTests{
    
    @Autowired
    PhoneNumberService service;
    
    @Autowired
    CustomerService customerService;
    
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
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void findByIdTest() {
        assertNotNull(service.findById(1));
        assertEquals("Renato", service.findById(1).getCustomer().getName());
        assertEquals("(48) 99630-0443", service.findById(1).getNumber());
    }

//    @Test
//    @DisplayName("Teste buscar cliente por ID inexistente")
//    @Sql({ "classpath:/resources/sqls/city.sql" })
//    @Sql({ "classpath:/resources/sqls/address.sql" })
//    @Sql({ "classpath:/resources/sqls/discount.sql" })
//    @Sql({ "classpath:/resources/sqls/customer.sql" })
//    void findByIdInvalidTest() {
//        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
//        assertEquals("Cliente 4 não existe", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Teste inserir cliente")
//    @Sql({ "classpath:/resources/sqls/city.sql" })
//    @Sql({ "classpath:/resources/sqls/address.sql" })
//    @Sql({ "classpath:/resources/sqls/discount.sql" })
//    void insertCustomerTest() {
//        Customer customer = new Customer(null, "Renato", addressService.findById(1), discountService.findById(1));
//        service.insert(customer);
//        var search = service.findById(1);
//        assertEquals(1, search.getId());
//        assertEquals("Renato", search.getName());
//        assertEquals("Tubarão", search.getAddress().getCity().getName());
//       
//    }
//
//    @Test
//    @DisplayName("Teste alterar cliente")
//    @Sql({ "classpath:/resources/sqls/city.sql" })
//    @Sql({ "classpath:/resources/sqls/address.sql" })
//    @Sql({ "classpath:/resources/sqls/discount.sql" })
//    @Sql({ "classpath:/resources/sqls/customer.sql" })
//    void updateCustomerByIdTest() {
//        var busca = service.findById(1);
//        assertEquals("Renato", busca.getName());
//        Customer altered = new Customer(1, "Luis", addressService.findById(1), discountService.findById(1));
//        service.update(altered);
//        altered = service.findById(1);
//        assertEquals("Luis", altered.getName());
//    }
//
//    @Test
//    @DisplayName("Teste alterar cliente inexistente")
//    @Sql({ "classpath:/resources/sqls/city.sql" })
//    @Sql({ "classpath:/resources/sqls/address.sql" })
//    @Sql({ "classpath:/resources/sqls/discount.sql" })
//    void updateCustomerByIdNonExistsTest() {
//        Customer altered = new Customer(1, "Renato", addressService.findById(1), discountService.findById(1));
//        var exception = assertThrows(ObjectNotFound.class, () -> service.update(altered));
//        assertEquals("Cliente 1 não existe", exception.getMessage());
//
//    }
//
//    @Test
//    @DisplayName("Teste apagar cliente por id")
//    @Sql({ "classpath:/resources/sqls/city.sql" })
//    @Sql({ "classpath:/resources/sqls/address.sql" })
//    @Sql({ "classpath:/resources/sqls/discount.sql" })
//    @Sql({ "classpath:/resources/sqls/customer.sql" })
//    void deleteCustomerByIdTest() {
//        service.delete(1);
//        var list = service.listAll();
//        assertEquals(2, list.size());
//        assertEquals("João", list.get(0).getName());
//    }
//
//    @Test
//    @DisplayName("Teste apagar cliente por id incorreto")
//    @Sql({ "classpath:/resources/sqls/city.sql" })
//    @Sql({ "classpath:/resources/sqls/address.sql" })
//    @Sql({ "classpath:/resources/sqls/discount.sql" })
//    @Sql({ "classpath:/resources/sqls/customer.sql" })
//    void deleteCustomerByIdNonExistsTest() {
//        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
//        assertEquals("Cliente 4 não existe", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Teste de listagem de todos os registros")
//    @Sql({ "classpath:/resources/sqls/city.sql" })
//    @Sql({ "classpath:/resources/sqls/address.sql" })
//    @Sql({ "classpath:/resources/sqls/discount.sql" })
//    @Sql({ "classpath:/resources/sqls/customer.sql" })
//    void listAllTest() {
//        assertNotNull(service.listAll());
//        assertEquals(3, service.listAll().size());
//    }
//
//    @Test
//    @DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
//    void listAllNonExistsTest() {
//        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
//        assertEquals("Nenhum cliente cadastrado", exception.getMessage());
//    }
//    
//    @Test
//    @DisplayName("Teste de busca de clientes por nome contendo")
//    @Sql({ "classpath:/resources/sqls/city.sql" })
//    @Sql({ "classpath:/resources/sqls/address.sql" })
//    @Sql({ "classpath:/resources/sqls/discount.sql" })
//    @Sql({ "classpath:/resources/sqls/customer.sql" })
//    void findByNameTest() {
//        var list = service.findByNameContainingIgnoreCase("re");
//        assertEquals(1, list.size());
//        assertEquals("Renato", list.get(0).getName());
//        assertEquals("Tubarão", list.get(0).getAddress().getCity().getName());
//    }

}

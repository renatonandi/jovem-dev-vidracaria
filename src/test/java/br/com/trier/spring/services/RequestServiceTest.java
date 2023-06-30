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
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class RequestServiceTest extends BaseTests{
	
	@Autowired
	RequestService service;
	
	@Autowired
    PhoneNumberService phoneNumberService;
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    DiscountService discountService;
    
    @Autowired
    AddressService addressService;
    
    @Autowired
    CityService cityService;
    
    @Test
    @DisplayName("Teste buscar pedido por ID")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void findByIdTest() {
        assertNotNull(service.findById(1));
        assertEquals("Renato", service.findById(1).getCustomer().getName());
        assertEquals("Uma porta de espelho bronze", service.findById(1).getDescription());
    }

    @Test
    @DisplayName("Teste buscar pedido por ID inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void findByIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
        assertEquals("O pedido 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir pedido")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void insertRequestTest() {
        Request insert = new Request(null, "Uma porta de espelho bronze", customerService.findById(1));
        service.insert(insert);
        var search = service.findById(1);
        assertEquals(1, search.getId());
        assertEquals("Uma porta de espelho bronze", search.getDescription());
        assertEquals("Renato", search.getCustomer().getName());
       
    }

    @Test
    @DisplayName("Teste alterar pedido")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void updateRequestByIdTest() {
        var busca = service.findById(1);
        assertEquals("Uma porta de espelho bronze", busca.getDescription());
        Request altered = new Request(1, "Uma porta de espelho fume", customerService.findById(1));
        service.update(altered);
        altered = service.findById(1);
        assertEquals("Uma porta de espelho fume", altered.getDescription());
    }

    @Test
    @DisplayName("Teste alterar pedido inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void updateRequestByIdNonExistsTest() {
        Request altered = new Request(1, "Uma porta de espelho fume", customerService.findById(1));
        var exception = assertThrows(ObjectNotFound.class, () -> service.update(altered));
        assertEquals("O pedido 1 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste apagar pedido por id")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void deleteRequestByIdTest() {
        service.delete(1);
        var list = service.listAll();
        assertEquals(2, list.size());
        assertEquals("Uma porta de espelho prata", list.get(0).getDescription());
    }

    @Test
    @DisplayName("Teste apagar pedido por id incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void deleteRequestByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
        assertEquals("O pedido 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void listAllTest() {
        assertNotNull(service.listAll());
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
    void listAllNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum pedido cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de pedido por descrição contendo")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void findByDescriptionTest() {
        var list = service.findByDescriptionContainingIgnoreCase("bronze");
        assertEquals("Uma porta de espelho bronze", list.get(0).getDescription());
        assertEquals("Renato", list.get(0).getCustomer().getName());
    }

    @Test
    @DisplayName("Teste de busca de pedido por descrição errada")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void findByDescriptionWrongTest() {
    	var exception = assertThrows(ObjectNotFound.class, () -> service.findByDescriptionContainingIgnoreCase("w"));
        assertEquals("Nenhum pedido encontrado para essa descrição w", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de pedido por cliente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void findByCustomerTest() {
        var list = service.findByCustomer(customerService.findById(1));
        assertEquals("Uma porta de espelho bronze", list.get(0).getDescription());
        assertEquals("Renato", list.get(0).getCustomer().getName());
    }

    @Test
    @DisplayName("Teste de busca de pedido por cliente invalido")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    void findByCustomerInvalidTest() {
    	var exception = assertThrows(ObjectNotFound.class, () -> service.findByCustomer(customerService.findById(3)));
        assertEquals("Nenhum pedido encontrado para esse cliente Beatriz", exception.getMessage());
    }

    
}

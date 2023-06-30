package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.models.PhoneNumber;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
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
    @DisplayName("Teste buscar numero de telefone por ID")
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

    @Test
    @DisplayName("Teste buscar numero de telefone por ID inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void findByIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
        assertEquals("O numero de telefone 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir numero de telefone")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void insertPhoneNumberTest() {
        PhoneNumber number = new PhoneNumber(null, "(48) 99630-0443", customerService.findById(1));
        service.insert(number);
        var search = service.findById(1);
        assertEquals(1, search.getId());
        assertEquals("(48) 99630-0443", search.getNumber());
        assertEquals("Renato", search.getCustomer().getName());
    }

    @Test
    @DisplayName("Teste inserir numero de telefone duplicado")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void insertPhoneNumberDuplicatedTest() {
    	PhoneNumber number = new PhoneNumber(null, "(48) 99630-0443", customerService.findById(1));
    	var exception = assertThrows(IntegrityViolation.class, () -> service.insert(number));
        assertEquals("Esse numero de telefone já está cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir numero de telefone inválido")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void insertPhoneNumberInvalidTest() {
    	PhoneNumber number = new PhoneNumber(null, "(48) 99630-04435", customerService.findById(1));
    	var exception = assertThrows(IntegrityViolation.class, () -> service.insert(number));
    	assertEquals("Número de telefone inálido", exception.getMessage());
    }

    @Test
    @DisplayName("Teste alterar numero de telefone")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void updatePhoneNumberByIdTest() {
        var busca = service.findById(1);
        assertEquals("(48) 99630-0443", busca.getNumber());
        PhoneNumber altered = new PhoneNumber(1, "(48) 99999-9998", customerService.findById(1));
        service.update(altered);
        altered = service.findById(1);
        assertEquals("(48) 99999-9998", altered.getNumber());
    }

    @Test
    @DisplayName("Teste alterar numero de telefone inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    void updatePhoneNumberByIdNonExistsTest() {
        PhoneNumber altered = new PhoneNumber(1, "(48) 99630-0443", customerService.findById(1));
        var exception = assertThrows(ObjectNotFound.class, () -> service.update(altered));
        assertEquals("O numero de telefone 1 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste apagar numero de telefone por id")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void deletePhoneNumberByIdTest() {
        service.delete(1);
        var list = service.listAll();
        assertEquals(2, list.size());
        assertEquals("(48) 99999-9999", list.get(0).getNumber());
    }

    @Test
    @DisplayName("Teste apagar numero de telefone por id incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void deletePhoneNumberByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
        assertEquals("O numero de telefone 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void listAllTest() {
        assertNotNull(service.listAll());
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
    void listAllNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum numero de telefone cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de numero de telefone por numero contendo")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void findByNumberTest() {
        var list = service.findByNumber("99630");
        assertEquals("Renato", list.getCustomer().getName());
        assertEquals("Tubarão", list.getCustomer().getAddress().getCity().getName());
    }

    @Test
    @DisplayName("Teste de busca de numero de telefone por numero errado")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void findByNumberWrongTest() {
    	var exception = assertThrows(ObjectNotFound.class, () -> service.findByNumber("0000"));
        assertEquals("Nenhum numero de telefone encontrado para esse numero 0000", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de numero de telefone por cliente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void findByCustomerTest() {
        var list = service.findByCustomer(customerService.findById(1));
        assertEquals("(48) 99630-0443", list.get(0).getNumber());
        assertEquals("Renato", list.get(0).getCustomer().getName());
    }

    @Test
    @DisplayName("Teste de busca de numero de telefone por cliente errado")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/phone_number.sql" })
    void findByCustomerWrongTest() {
    	var exception = assertThrows(ObjectNotFound.class, () -> service.findByCustomer(customerService.findById(3)));
        assertEquals("Nenhum cliente cadastrado com esse numero", exception.getMessage());
    }

}

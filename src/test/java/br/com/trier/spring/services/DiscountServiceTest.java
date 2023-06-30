package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.models.Discount;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class DiscountServiceTest extends BaseTests{
    
    @Autowired
    DiscountService service;
    
    @Test
    @DisplayName("Teste buscar desconto por ID")
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void findByIdTest() {
        assertNotNull(service.findById(1));
        assertEquals(5, service.findById(1).getDiscount());
    }

    @Test
    @DisplayName("Teste buscar descotno por ID inexistente")
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void findByIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
        assertEquals("O desconto 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir desconto")
    void insertDiscountTest() {
        Discount discount = new Discount(null, 5);
        service.insert(discount);
        var search = service.findById(1);
        assertEquals(1, search.getId());
        assertEquals(5, search.getDiscount());
       
    }

    @Test
    @DisplayName("Teste inserir desconto com valor inválido")
    void insertDiscountInvalidTest() {
        Discount discount = new Discount(null, -100);
        var exception = assertThrows(IntegrityViolation.class, () -> service.insert(discount));
        assertEquals("Desconto inválido. Valor não pode ser menor ou igual a zero", exception.getMessage());
        Discount discount2 = new Discount(null, 0);
        var exception2 = assertThrows(IntegrityViolation.class, () -> service.insert(discount2));
        assertEquals("Desconto inválido. Valor não pode ser menor ou igual a zero", exception2.getMessage());
    }

    @Test
    @DisplayName("Teste alterar desconto")
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void updateDiscountByIdTest() {
        var busca = service.findById(1);
        assertEquals(5, busca.getDiscount());
        Discount alteredDiscount = new Discount(1, 10);
        service.update(alteredDiscount);
        alteredDiscount = service.findById(1);
        assertEquals(10, alteredDiscount.getDiscount());
    }

    @Test
    @DisplayName("Teste alterar desconto inexistente")
    void updateDiscountByIdNonExistsTest() {
        Discount alteredDiscount = new Discount(1, 10);
        var exception = assertThrows(ObjectNotFound.class, () -> service.update(alteredDiscount));
        assertEquals("O desconto 1 não existe", exception.getMessage());

    }

    @Test
    @DisplayName("Teste apagar desconto por id")
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void deleteDiscountByIdTest() {
        service.delete(1);
        var list = service.listAll();
        assertEquals(2, list.size());
        assertEquals(10, list.get(0).getDiscount());
    }

    @Test
    @DisplayName("Teste apagar desconto por id incorreto")
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void deleteDiscountByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
        assertEquals("O desconto 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros")
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void listAllTest() {
        assertNotNull(service.listAll());
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
    void listAllNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum desconto cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de descontos por valor de desconto")
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void findByDiscountTest() {
        var list = service.findByDiscount(5);
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Teste de busca de descontos por valor de desconto inexistente")
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    void findByDiscountNonExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByDiscount(1));
        assertEquals("Nenhum desconto encontrado para esse valor 1", exception.getMessage());
    }

}

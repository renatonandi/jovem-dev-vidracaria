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
import br.com.trier.spring.models.Sale;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class SaleServiceTest extends BaseTests{
	
	@Autowired
	SaleService service;
	
	@Autowired
    CustomerService customerService;
    
    @Autowired
    DiscountService discountService;
    
    @Autowired
    AddressService addressService;
    
    @Autowired
    CityService cityService;
    
    @Autowired
    TypeService typeService;
    
    @Autowired
    ProductService productService;
    
    @Autowired
    RequestService requestService;
    
    @Test
    @DisplayName("Teste buscar venda por ID")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void findByIdTest() {
        assertNotNull(service.findById(1));
        assertEquals(1.563, service.findById(1).getSize());
        assertEquals("Uma porta de espelho bronze", service.findById(1).getRequest().getDescription());
        assertEquals("Espelho", service.findById(1).getProduct().getName());
    }

    @Test
    @DisplayName("Teste buscar venda por ID inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void findByIdInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(4));
        assertEquals("A venda 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste inserir venda")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    void insertSaleTest() {
        Sale insert = new Sale(null, 1.563, requestService.findById(1), productService.findById(1));
        service.insert(insert);
        var search = service.findById(1);
        assertEquals("Espelho", search.getProduct().getName());
        assertEquals("Uma porta de espelho bronze", search.getRequest().getDescription());
       
    }

    @Test
    @DisplayName("Teste alterar venda")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void updateSaleByIdTest() {
        var busca = service.findById(1);
        assertEquals(1.563, busca.getSize());
        Sale altered = new Sale(1, 2.563, requestService.findById(1), productService.findById(1));
        service.update(altered);
        altered = service.findById(1);
        assertEquals(2.563, altered.getSize());
    }

    @Test
    @DisplayName("Teste alterar venda inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    void updateSaleByIdNonExistsTest() {
        Sale altered = new Sale(1, 2.563, requestService.findById(1), productService.findById(1));
        var exception = assertThrows(ObjectNotFound.class, () -> service.update(altered));
        assertEquals("A venda 1 não existe", exception.getMessage());

    }

    @Test
    @DisplayName("Teste apagar venda por id")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void deleteSaleByIdTest() {
        service.delete(1);
        var list = service.listAll();
        assertEquals(2, list.size());
        assertEquals(2.243, list.get(0).getSize());
    }

    @Test
    @DisplayName("Teste apagar venda por id incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void deleteSaleByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(4));
        assertEquals("A venda 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void listAllTest() {
        assertNotNull(service.listAll());
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste de listagem de todos os registros sem nem um cadastrado")
    void listAllNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhuma venda cadastrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de vendas por pedido")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void findByRequestTest() {
        var list = service.findByRequest(requestService.findById(1));
        assertEquals(2, list.size());
        assertEquals(1.563, list.get(0).getSize());
        assertEquals(2.243, list.get(1).getSize());
    }

    @Test
    @DisplayName("Teste de busca de vendas por pedido inexistente")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void findByRequestNonExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByRequest(requestService.findById(3)));
        assertEquals("Nenhuma venda encontrado para esse pedido", exception.getMessage());
    }
    
    @Test
    @DisplayName("Teste de busca de vendas por produtos")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void findByProductTest() {
        var list = service.findByProduct(productService.findById(2));
        assertEquals(2, list.size());
        assertEquals(2.243, list.get(0).getSize());
        assertEquals(1.421, list.get(1).getSize());
    }

    @Test
    @DisplayName("Teste de busca de vendas por produto incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void findByProductWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findByProduct(productService.findById(3)));
        assertEquals("Nenhuma venda encontrado para esse produto", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de busca de vendas por tamanho")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void findByDiscountTest() {
        var list = service.findBySize(2.243);
        assertEquals(1, list.size());
        assertEquals(2.243, list.get(0).getSize());
        assertEquals("Espelho", list.get(0).getProduct().getName());
        assertEquals("Uma porta de espelho bronze", list.get(0).getRequest().getDescription());
    }

    @Test
    @DisplayName("Teste de busca de clientes por tamanho incorreto")
    @Sql({ "classpath:/resources/sqls/city.sql" })
    @Sql({ "classpath:/resources/sqls/address.sql" })
    @Sql({ "classpath:/resources/sqls/discount.sql" })
    @Sql({ "classpath:/resources/sqls/customer.sql" })
    @Sql({ "classpath:/resources/sqls/request.sql" })
    @Sql({ "classpath:/resources/sqls/type.sql" })
    @Sql({ "classpath:/resources/sqls/product.sql" })
    @Sql({ "classpath:/resources/sqls/sale.sql" })
    void findByDiscountWrongTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findBySize(1.123));
        assertEquals("Nenhuma venda encontrado para esse tamanho", exception.getMessage());
    }


}

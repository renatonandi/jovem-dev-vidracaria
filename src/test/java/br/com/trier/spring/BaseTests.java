package br.com.trier.spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.spring.services.AddressService;
import br.com.trier.spring.services.CityService;
import br.com.trier.spring.services.CustomerService;
import br.com.trier.spring.services.DiscountService;
import br.com.trier.spring.services.PhoneNumberService;
import br.com.trier.spring.services.ProductService;
import br.com.trier.spring.services.RequestService;
import br.com.trier.spring.services.SaleService;
import br.com.trier.spring.services.TypeService;
import br.com.trier.spring.services.impl.AddressServiceImpl;
import br.com.trier.spring.services.impl.CityServiceImpl;
import br.com.trier.spring.services.impl.CustomerServiceImpl;
import br.com.trier.spring.services.impl.DiscountServiceImpl;
import br.com.trier.spring.services.impl.PhoneNumberServiceImpl;
import br.com.trier.spring.services.impl.ProductServiceImpl;
import br.com.trier.spring.services.impl.RequestServiceImpl;
import br.com.trier.spring.services.impl.SaleServiceImpl;
import br.com.trier.spring.services.impl.TypeServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {
    
    @Bean
    public TypeService typeService() {
        return new TypeServiceImpl();
    }

    @Bean
    public CityService cityService() {
        return new CityServiceImpl();
    }

    @Bean
    public AddressService addressService() {
        return new AddressServiceImpl();
    }

    @Bean
    public DiscountService discountService() {
        return new DiscountServiceImpl();
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerServiceImpl();
    }

    @Bean
    public SaleService saleService() {
    	return new SaleServiceImpl();
    }

    @Bean
    public RequestService requestService() {
    	return new RequestServiceImpl();
    }

    @Bean
    public ProductService productService() {
    	return new ProductServiceImpl();
    }

    @Bean
    public PhoneNumberService phoneNumberService() {
    	return new PhoneNumberServiceImpl();
    }
    
    

}

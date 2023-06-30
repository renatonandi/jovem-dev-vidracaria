package br.com.trier.spring.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.models.City;
import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.Sale;
import br.com.trier.spring.models.Type;
import br.com.trier.spring.models.DTO.CustomerByCityDTO;
import br.com.trier.spring.models.DTO.CustomerDTO;
import br.com.trier.spring.models.DTO.ProductByTypeAndSizeDTO;
import br.com.trier.spring.models.DTO.SaleDTO;
import br.com.trier.spring.services.AddressService;
import br.com.trier.spring.services.CityService;
import br.com.trier.spring.services.CustomerService;
import br.com.trier.spring.services.ProductService;
import br.com.trier.spring.services.SaleService;
import br.com.trier.spring.services.TypeService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@RestController
@RequestMapping("/report")
public class ReportResource {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private TypeService typeService;
    
    @Autowired
    private SaleService saleService;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private AddressService addressService;
    
    
    @GetMapping("/product-by-type-and-size/{typeId}/{size}")
    public ResponseEntity<ProductByTypeAndSizeDTO> findProductByTypeAndSize(@PathVariable Integer typeId, @PathVariable Double size){
        Type type = typeService.findById(typeId);
        
        List<SaleDTO> products = productService.findByType(type).stream().flatMap(sale -> {
            try {
                return saleService.findByProduct(sale).stream();
            } catch (ObjectNotFound e) {
                return Stream.empty();
            }
        }).filter(sale -> sale.getSize() > size).map(Sale::toDTO).toList();
        
        return ResponseEntity.ok(new ProductByTypeAndSizeDTO(type.getDescription(), products.size(), products));
    }
    
    
    @GetMapping("/customer-by-city/{cityId}")
    public ResponseEntity<CustomerByCityDTO> findCustomerByCity(@PathVariable Integer cityId){
        City city = cityService.findById(cityId);
        
        List<CustomerDTO> customers = addressService.findByCity(city).stream().flatMap(customer -> {
            try {
                return customerService.findByAddress(customer).stream();
            } catch (ObjectNotFound e) {
                return Stream.empty();
            }
        }).filter(customer -> customer.getId() != 0).map(Customer::toDTO).toList();
        
        return ResponseEntity.ok(new CustomerByCityDTO(city.getName(), city.getUf(), customers.size(), customers));
    }
}

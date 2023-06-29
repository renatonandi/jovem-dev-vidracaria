package br.com.trier.spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.DTO.CustomerDTO;
import br.com.trier.spring.services.AddressService;
import br.com.trier.spring.services.CustomerService;
import br.com.trier.spring.services.DiscountService;

@RestController
@RequestMapping("/cliente")
public class CustomerResource {

    @Autowired
    private CustomerService service;
    
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private DiscountService discountService;
    
    @PostMapping
    public ResponseEntity<CustomerDTO> insert(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(service.insert(new Customer(customerDTO, addressService.findById(customerDTO.getAddressId()), discountService.findById(customerDTO.getDiscountId()))).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO, addressService.findById(customerDTO.getId()), discountService.findById(customerDTO.getId()));
    	customer.setId(id);
        return ResponseEntity.ok(service.update(customer).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map((customer -> customer.toDTO())).toList());
    }
    
    @GetMapping("/nome/{name}")
    public ResponseEntity<List<Customer>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameContainingIgnoreCase(name));
    }

    @GetMapping("/endereco/{idAddress}")
    public ResponseEntity<List<Customer>> findByAddress(@PathVariable Integer idAddress) {
        return ResponseEntity.ok(service.findByAddress(addressService.findById(idAddress)));
    }

    @GetMapping("/desconto/{idDiscount}")
    public ResponseEntity<List<Customer>> findByDiscount(@PathVariable Integer idDiscount) {
        return ResponseEntity.ok(service.findByDiscount(discountService.findById(idDiscount)));
    }
    
}

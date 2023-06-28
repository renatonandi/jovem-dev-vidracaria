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
    public ResponseEntity<Customer> insert(@RequestBody Customer customer) {
        addressService.findById(customer.getAddress().getId());
        discountService.findById(customer.getDiscount().getId());
        return ResponseEntity.ok(service.insert(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Integer id, @RequestBody Customer customer) {
        customer.setId(id);
        return ResponseEntity.ok(service.update(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listAll() {
        return ResponseEntity.ok(service.listAll());
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

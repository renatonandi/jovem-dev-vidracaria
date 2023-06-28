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

import br.com.trier.spring.models.PhoneNumber;
import br.com.trier.spring.services.CustomerService;
import br.com.trier.spring.services.PhoneNumberService;

@RestController
@RequestMapping("/telefone")
public class PhoneNumberResource {

    @Autowired
    private PhoneNumberService service;
    
    @Autowired
    private CustomerService customerService;
    
    
    @PostMapping
    public ResponseEntity<PhoneNumber> insert(@RequestBody PhoneNumber phoneNumber) {
        customerService.findById(phoneNumber.getCustomer().getId());
        return ResponseEntity.ok(service.insert(phoneNumber));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhoneNumber> update(@PathVariable Integer id, @RequestBody PhoneNumber phoneNumber) {
        phoneNumber.setId(id);
        return ResponseEntity.ok(service.update(phoneNumber));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumber> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PhoneNumber>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
    
}

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

import br.com.trier.spring.models.Request;
import br.com.trier.spring.services.CustomerService;
import br.com.trier.spring.services.RequestService;

@RestController
@RequestMapping("/pedido")
public class RequestResource {
    
    @Autowired
    private RequestService service;
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    public ResponseEntity<Request> insert(@RequestBody Request request) {
        return ResponseEntity.ok(service.insert(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> update(@PathVariable Integer id, @RequestBody Request request) {
        request.setId(id);
        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Request>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
    
    @GetMapping("/descricao/{description}")
    public ResponseEntity<List<Request>> findByDescription(@PathVariable String description) {
        return ResponseEntity.ok(service.findByDescriptionContainingIgnoreCase(description));
    }
    
    @GetMapping("/cliente/{idCustomer}")
    public ResponseEntity<List<Request>> findByDescription(@PathVariable Integer idCustomer) {
        return ResponseEntity.ok(service.findByCustomer(customerService.findById(idCustomer)));
    }

}

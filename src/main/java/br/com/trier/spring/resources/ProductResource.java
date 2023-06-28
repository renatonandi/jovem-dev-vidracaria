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

import br.com.trier.spring.models.Product;
import br.com.trier.spring.services.ProductService;
import br.com.trier.spring.services.TypeService;

@RestController
@RequestMapping("/produto")
public class ProductResource {
    
    @Autowired
    private ProductService service;
    
    @Autowired
    private TypeService typeService;
    
    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody Product product) {
        return ResponseEntity.ok(service.insert(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product product) {
        product.setId(id);
        return ResponseEntity.ok(service.update(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Product>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
    
    @GetMapping("/nome/{name}")
    public ResponseEntity<List<Product>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameContainingIgnoreCase(name));
    }

    @GetMapping("/tipo/{idType}")
    public ResponseEntity<List<Product>> findByType(@PathVariable Integer idType) {
        return ResponseEntity.ok(service.findByType(typeService.findById(idType)));
    }

}

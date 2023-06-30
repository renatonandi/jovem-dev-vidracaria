package br.com.trier.spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.models.Discount;
import br.com.trier.spring.services.DiscountService;

@RestController
@RequestMapping("/desconto")
public class DiscountResource {
    
    @Autowired
    private DiscountService service;
    
    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    public ResponseEntity<Discount> insert(@RequestBody Discount discount) {
        return ResponseEntity.ok(service.insert(discount));
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<Discount> update(@PathVariable Integer id, @RequestBody Discount discount) {
        discount.setId(id);
        return ResponseEntity.ok(service.update(discount));
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Secured({ "ROLE_USER" })
    @GetMapping("/{id}")
    public ResponseEntity<Discount> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Secured({ "ROLE_USER" })
    @GetMapping
    public ResponseEntity<List<Discount>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Secured({ "ROLE_USER" })
    @GetMapping("/desconto/{discount}")
    public ResponseEntity<List<Discount>> findByDescription(@PathVariable Integer discount) {
        return ResponseEntity.ok(service.findByDiscount(discount));
    }

}

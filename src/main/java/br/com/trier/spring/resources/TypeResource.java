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

import br.com.trier.spring.models.Type;
import br.com.trier.spring.services.TypeService;

@RestController
@RequestMapping("/tipo")
public class TypeResource {
    
    @Autowired
    private TypeService service;
    
    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    public ResponseEntity<Type> insert(@RequestBody Type type) {
        return ResponseEntity.ok(service.insert(type));
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<Type> update(@PathVariable Integer id, @RequestBody Type type) {
        type.setId(id);
        return ResponseEntity.ok(service.update(type));
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Secured({ "ROLE_USER" })
    @GetMapping("/{id}")
    public ResponseEntity<Type> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Secured({ "ROLE_USER" })
    @GetMapping
    public ResponseEntity<List<Type>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
    
    @Secured({ "ROLE_USER" })
    @GetMapping("/descricao/{description}")
    public ResponseEntity<List<Type>> findByDescription(@PathVariable String description) {
        return ResponseEntity.ok(service.findByDescriptionContainingIgnoreCase(description));
    }

}

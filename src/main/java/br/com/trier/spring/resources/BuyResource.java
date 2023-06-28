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

import br.com.trier.spring.models.Buy;
import br.com.trier.spring.services.BuyService;
import br.com.trier.spring.services.ProductRequestService;

@RestController
@RequestMapping("/compra")
public class BuyResource {
    
    @Autowired
    private BuyService service;
    
    @Autowired
    private ProductRequestService productRequestService;
    
    @PostMapping
    public ResponseEntity<Buy> insert(@RequestBody Buy buy) {
        productRequestService.findById(buy.getId());
        return ResponseEntity.ok(service.insert(buy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buy> update(@PathVariable Integer id, @RequestBody Buy buy) {
        buy.setId(id);
        return ResponseEntity.ok(service.update(buy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buy> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Buy>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
    
    @GetMapping("/valor-total/{amount}")
    public ResponseEntity<List<Buy>> findByAmount(@PathVariable Double amount) {
        return ResponseEntity.ok(service.findByAmount(amount));
    }

    @GetMapping("/valor-total/entre/{firstAmount}/{lastAmount}")
    public ResponseEntity<List<Buy>> findByAmountBetween(@PathVariable Double firstAmount, @PathVariable Double lastAmount) {
        return ResponseEntity.ok(service.findByAmountBetween(firstAmount, lastAmount));
    }

    @GetMapping("/produto-pedido/{idProductRequest}")
    public ResponseEntity<List<Buy>> findByAmountBetween(@PathVariable Integer idProductRequest) {
        return ResponseEntity.ok(service.findByProductRequestContaining(productRequestService.findById(idProductRequest)));
    }

}

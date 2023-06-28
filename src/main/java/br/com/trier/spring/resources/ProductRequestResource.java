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

import br.com.trier.spring.models.ProductRequest;
import br.com.trier.spring.services.ProductRequestService;
import br.com.trier.spring.services.ProductService;
import br.com.trier.spring.services.RequestService;

@RestController
@RequestMapping("/produto-pedido")
public class ProductRequestResource {
    
    @Autowired
    private ProductRequestService service;
    
    @Autowired
    private RequestService requestService;
    
    @Autowired
    private ProductService productService;
    
    @PostMapping
    public ResponseEntity<ProductRequest> insert(@RequestBody ProductRequest productRequest) {
        requestService.findById(productRequest.getRequest().getId());
        productService.findById(productRequest.getProduct().getId());
        return ResponseEntity.ok(service.insert(productRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRequest> update(@PathVariable Integer id, @RequestBody ProductRequest productRequest) {
        productRequest.setId(id);
        return ResponseEntity.ok(service.update(productRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRequest> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductRequest>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
    
    @GetMapping("/pedido/{idRequest}")
    public ResponseEntity<List<ProductRequest>> findByRequest(@PathVariable Integer idRequest) {
        return ResponseEntity.ok(service.findByRequest(requestService.findById(idRequest)));
    }

    @GetMapping("/produto/{idProduct}")
    public ResponseEntity<List<ProductRequest>> findByProduct(@PathVariable Integer idProduct) {
        return ResponseEntity.ok(service.findByProduct(productService.findById(idProduct)));
    }

    @GetMapping("/tamanho/{size}")
    public ResponseEntity<List<ProductRequest>> findByProduct(@PathVariable Double size) {
        return ResponseEntity.ok(service.findBySize(size));
    }

}

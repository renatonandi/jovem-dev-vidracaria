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

import br.com.trier.spring.models.Sale;
import br.com.trier.spring.models.DTO.SaleDTO;
import br.com.trier.spring.services.SaleService;
import br.com.trier.spring.services.ProductService;
import br.com.trier.spring.services.RequestService;

@RestController
@RequestMapping("/venda")
public class SaleResource {
    
    @Autowired
    private SaleService service;
    
    @Autowired
    private RequestService requestService;
    
    @Autowired
    private ProductService productService; 
    
    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    public ResponseEntity<SaleDTO> insert(@RequestBody SaleDTO productRequestDTO) { 
        return ResponseEntity.ok(service.insert(new Sale(productRequestDTO, requestService.findById(productRequestDTO.getRequestId()), productService.findById(productRequestDTO.getProductId()))).toDTO());
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@PathVariable Integer id, @RequestBody SaleDTO productRequestDTO) {
    	Sale productRequest = new Sale(productRequestDTO, requestService.findById(productRequestDTO.getRequestId()), productService.findById(productRequestDTO.getProductId()));
    	productRequest.setId(id);
        return ResponseEntity.ok(service.update(productRequest).toDTO());
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Secured({ "ROLE_USER" })
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @Secured({ "ROLE_USER" })
    @GetMapping
    public ResponseEntity<List<SaleDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map((productRequest -> productRequest.toDTO())).toList());
    }
    
    @Secured({ "ROLE_USER" })
    @GetMapping("/pedido/{idRequest}")
    public ResponseEntity<List<Sale>> findByRequest(@PathVariable Integer idRequest) {
        return ResponseEntity.ok(service.findByRequest(requestService.findById(idRequest)));
    }

    @Secured({ "ROLE_USER" })
    @GetMapping("/produto/{idProduct}")
    public ResponseEntity<List<Sale>> findByProduct(@PathVariable Integer idProduct) {
        return ResponseEntity.ok(service.findByProduct(productService.findById(idProduct)));
    }

    @Secured({ "ROLE_USER" })
    @GetMapping("/tamanho/{size}")
    public ResponseEntity<List<Sale>> findByProduct(@PathVariable Double size) {
        return ResponseEntity.ok(service.findBySize(size));
    }

}

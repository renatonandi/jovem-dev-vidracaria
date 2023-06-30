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

import br.com.trier.spring.models.Request;
import br.com.trier.spring.models.DTO.RequestDTO;
import br.com.trier.spring.services.CustomerService;
import br.com.trier.spring.services.RequestService;

@RestController
@RequestMapping("/pedido")
public class RequestResource {
    
    @Autowired
    private RequestService service;
    
    @Autowired
    private CustomerService customerService;
    
    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    public ResponseEntity<RequestDTO> insert(@RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok(service.insert(new Request(requestDTO, customerService.findById(requestDTO.getCustomerId()))).toDTO());
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<RequestDTO> update(@PathVariable Integer id, @RequestBody RequestDTO requestDTO) {
        Request request = new Request(requestDTO, customerService.findById(requestDTO.getCustomerId()));
    	request.setId(id);
        return ResponseEntity.ok(service.update(request).toDTO());
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Secured({ "ROLE_USER" })
    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @Secured({ "ROLE_USER" })
    @GetMapping
    public ResponseEntity<List<RequestDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map((request -> request.toDTO())).toList());
    }
    
    @Secured({ "ROLE_USER" })
    @GetMapping("/descricao/{description}")
    public ResponseEntity<List<Request>> findByDescription(@PathVariable String description) {
        return ResponseEntity.ok(service.findByDescriptionContainingIgnoreCase(description));
    }
    
    @Secured({ "ROLE_USER" })
    @GetMapping("/cliente/{idCustomer}")
    public ResponseEntity<List<Request>> findByDescription(@PathVariable Integer idCustomer) {
        return ResponseEntity.ok(service.findByCustomer(customerService.findById(idCustomer)));
    }

}

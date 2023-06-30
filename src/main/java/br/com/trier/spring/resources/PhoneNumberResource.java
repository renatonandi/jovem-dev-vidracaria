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

import br.com.trier.spring.models.PhoneNumber;
import br.com.trier.spring.models.DTO.PhoneNumberDTO;
import br.com.trier.spring.services.CustomerService;
import br.com.trier.spring.services.PhoneNumberService;

@RestController
@RequestMapping("/telefone")
public class PhoneNumberResource {

	@Autowired
	private PhoneNumberService service;

	@Autowired
	private CustomerService customerService;

	@Secured({ "ROLE_ADMIN" })
	@PostMapping
	public ResponseEntity<PhoneNumberDTO> insert(@RequestBody PhoneNumberDTO phoneNumberDTO) {
		return ResponseEntity.ok(service
				.insert(new PhoneNumber(phoneNumberDTO, customerService.findById(phoneNumberDTO.getCustomerId())))
				.toDTO());
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<PhoneNumberDTO> update(@PathVariable Integer id, @RequestBody PhoneNumberDTO phoneNumberDTO) {
		PhoneNumber phoneNumber = new PhoneNumber(phoneNumberDTO, customerService.findById(phoneNumberDTO.getCustomerId()));
		phoneNumber.setId(id);
		return ResponseEntity.ok(service.update(phoneNumber).toDTO());
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/{id}")
	public ResponseEntity<PhoneNumberDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping
	public ResponseEntity<List<PhoneNumberDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((phoneNumber -> phoneNumber.toDTO())).toList());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/cliente/{idCustomer}")
	public ResponseEntity<List<PhoneNumber>> findByCustomer(@PathVariable Integer idCustomer) {
		return ResponseEntity.ok(service.findByCustomer(customerService.findById(idCustomer)));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/{number}")
	public ResponseEntity<PhoneNumber> findByNumber(@PathVariable String number) {
		return ResponseEntity.ok(service.findByNumber(number));
	}

}

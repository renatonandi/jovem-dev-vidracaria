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

import br.com.trier.spring.models.Address;
import br.com.trier.spring.models.City;
import br.com.trier.spring.services.AddressService;
import br.com.trier.spring.services.CityService;

@RestController
@RequestMapping("/endereco")
public class AddressResource {

	@Autowired
	private AddressService service;
	
	@Autowired
	private CityService cityService;

	@PostMapping
	public ResponseEntity<Address> insert(@RequestBody Address address) {
		cityService.findById(address.getCity().getId());
		return ResponseEntity.ok(service.insert(address));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Address> update(@PathVariable Integer id, @RequestBody Address address) {
		address.setId(id);
		return ResponseEntity.ok(service.update(address));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Address> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Address>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@GetMapping("/rua/{street}")
	public ResponseEntity<List<Address>> findByStreet(@PathVariable String street) {
		return ResponseEntity.ok(service.findByStreetContainingIgnoreCase(street));
	}

	@GetMapping("/bairro/{neighborhood}")
	public ResponseEntity<List<Address>> findByNeighborhood(@PathVariable String neighborhood) {
		return ResponseEntity.ok(service.findByNeighborhoodContainingIgnoreCase(neighborhood));
	}

	@GetMapping("/bairro/rua/{neighborhood}/{street}")
	public ResponseEntity<List<Address>> findByNeighborhoodAndStreet(@PathVariable String neighborhood, @PathVariable String street) {
		return ResponseEntity.ok(service.findByNeighborhoodContainingIgnoreCaseAndStreetContainingIgnoreCase(neighborhood, street));
	}

	@GetMapping("/cidade/{idCity}")
	public ResponseEntity<List<Address>> findByCity(@PathVariable Integer idCity) {
		return ResponseEntity.ok(service.findByCityContaining(cityService.findById(idCity)));
	}

}

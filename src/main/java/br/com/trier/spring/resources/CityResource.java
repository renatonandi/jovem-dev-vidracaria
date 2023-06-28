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

import br.com.trier.spring.models.City;
import br.com.trier.spring.services.CityService;

@RestController
@RequestMapping("/cidade")
public class CityResource {
	
	@Autowired
	private CityService service;
	
	@PostMapping
	public ResponseEntity<City> insert(@RequestBody City city) {
		return ResponseEntity.ok(service.insert(city));
	}

	@PutMapping("/{id}")
	public ResponseEntity<City> update(@PathVariable Integer id, @RequestBody City city) {
		city.setId(id);
		return ResponseEntity.ok(service.update(city));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<City> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<City>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/nome/{name}")
	public ResponseEntity<List<City>> findByName(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameContainingIgnoreCase(name));
	}

	@GetMapping("/nome/uf/{name}/{uf}")
	public ResponseEntity<List<City>> findByNameAndUf(@PathVariable String name, @PathVariable String uf) {
		return ResponseEntity.ok(service.findByNameContainingIgnoreCaseAndUfIgnoreCase(name, uf));
	}
	
	@GetMapping("/uf/{uf}")
	public ResponseEntity<List<City>> findByUf(@PathVariable String uf) {
		return ResponseEntity.ok(service.findByUf(uf));
	}
	
	

}

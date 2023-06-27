package com.trier.gerenciamentoestoque.resources;



import java.util.List;
import java.util.Optional;

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

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.services.ClientService;


@RestController
@RequestMapping("/client")
public class ClientResource {

	@Autowired
	private ClientService service;

	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client client) {
		return ResponseEntity.ok(service.insert(client));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@PathVariable Integer id, @RequestBody Client client) {
		client.setId(id);
		return ResponseEntity.ok(service.update(client));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Client>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@GetMapping("/name-order/{name}")
	public ResponseEntity<List<Client>> findByNameOrderByNameDesc(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameOrderByNameDesc(name));
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<Optional<Client>> findByCpf(@PathVariable String cpf) {
		return ResponseEntity.ok(service.findByCpf(cpf));
	}
	
	@GetMapping("/age/{age}")
	public ResponseEntity<List<Client>> findByAge(@PathVariable Integer age) {
		return ResponseEntity.ok(service.findByAge(age));
	}
	
	@GetMapping("/age-between/{ageI}/{ageF}")
	public ResponseEntity<List<Client>> findByAgeBetween(@PathVariable Integer ageI, @PathVariable Integer ageF) {
		return ResponseEntity.ok(service.findByAgeBetween(ageI, ageF));
	}
	
	@GetMapping("/number/{number}")
	public ResponseEntity<Optional<Client>> findByNumber(@PathVariable Long number) {
		return ResponseEntity.ok(service.findByNumber(number));
	}

}

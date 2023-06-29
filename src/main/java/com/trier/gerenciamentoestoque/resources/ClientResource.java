package com.trier.gerenciamentoestoque.resources;

import java.util.List;
import java.util.Optional;

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

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientResource {

	@Autowired
	private ClientService service;

	@Secured({ "ROLE_USER" })
	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client client) {
		return ResponseEntity.ok(service.insert(client));
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@PathVariable Integer id, @RequestBody Client client) {
		client.setId(id);
		return ResponseEntity.ok(service.update(client));
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_USER" })
	@GetMapping
	public ResponseEntity<List<Client>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/name-containing/{name}")
	public ResponseEntity<List<Client>> findByNameContainingOrderByNameDesc(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameContainingOrderByNameDesc(name));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<Optional<Client>> findByCpf(@PathVariable String cpf) {
		return ResponseEntity.ok(service.findByCpf(cpf));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/age/{age}")
	public ResponseEntity<List<Client>> findByAge(@PathVariable Integer age) {
		return ResponseEntity.ok(service.findByAge(age));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/age-between/{ageI}/{ageF}")
	public ResponseEntity<List<Client>> findByAgeBetween(@PathVariable Integer ageI, @PathVariable Integer ageF) {
		return ResponseEntity.ok(service.findByAgeBetween(ageI, ageF));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/number/{number}")
	public ResponseEntity<Optional<Client>> findByNumber(@PathVariable Long number) {
		return ResponseEntity.ok(service.findByNumber(number));
	}

}

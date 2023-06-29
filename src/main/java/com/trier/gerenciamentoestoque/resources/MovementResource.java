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

import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.dto.MovementDTO;
import com.trier.gerenciamentoestoque.services.EntryService;
import com.trier.gerenciamentoestoque.services.MovementService;
import com.trier.gerenciamentoestoque.services.OutputService;
import com.trier.gerenciamentoestoque.utils.DateUtils;

@RestController
@RequestMapping("/movement")
public class MovementResource {

	@Autowired
	private MovementService service;

	@Autowired
	private EntryService entryService;

	@Autowired
	private OutputService outputService;

	@Secured({ "ROLE_USER" })
	@GetMapping("/{id}")
	public ResponseEntity<MovementDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping
	public ResponseEntity<MovementDTO> insert(@RequestBody MovementDTO movement) {
		Movement m = new Movement(movement, entryService.findById(movement.getEntryId()),
				outputService.findById(movement.getOutputId()));
		return ResponseEntity.ok(service.insert(m).toDTO());
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<MovementDTO> update(@PathVariable Integer id, @RequestBody MovementDTO movement) {
		Movement m = new Movement(movement, entryService.findById(movement.getEntryId()),
				outputService.findById(movement.getOutputId()));
		m.setId(id);
		return ResponseEntity.ok(service.update(m).toDTO());
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_USER" })
	@GetMapping
	public ResponseEntity<List<MovementDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((movement) -> movement.toDTO()).toList());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/datetime/{datetime}")
	public ResponseEntity<List<MovementDTO>> findByDateTime(@PathVariable String datetime) {
		List<Movement> lista = service.findByDateTime(DateUtils.strToZonedDateTime(datetime));
		return ResponseEntity.ok(lista.stream().map((Movement) -> Movement.toDTO()).toList());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/entry/{entryId}")
	public ResponseEntity<List<MovementDTO>> findByEntry(@PathVariable Integer entryId) {
		List<Movement> lista = service.findByEntry(entryService.findById(entryId));
		return ResponseEntity.ok(lista.stream().map((Movement) -> Movement.toDTO()).toList());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/output/{outputId}")
	public ResponseEntity<List<MovementDTO>> findByOutput(@PathVariable Integer outputId) {
		List<Movement> lista = service.findByOutput(outputService.findById(outputId));
		return ResponseEntity.ok(lista.stream().map((Movement) -> Movement.toDTO()).toList());
	}
}

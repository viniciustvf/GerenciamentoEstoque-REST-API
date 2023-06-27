package com.trier.gerenciamentoestoque.resources;



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

import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.dto.OutputDTO;
import com.trier.gerenciamentoestoque.services.ClientService;
import com.trier.gerenciamentoestoque.services.OutputService;
import com.trier.gerenciamentoestoque.services.SellerService;


@RestController
@RequestMapping("/output")
public class OutputResource {

	@Autowired
	private OutputService service;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private SellerService sellerService;

	@GetMapping("/{id}")
	public ResponseEntity<OutputDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@PostMapping
	public ResponseEntity<OutputDTO> insert(@RequestBody OutputDTO output) {
		Output o = new Output(output, clientService.findById(output.getClientId()), sellerService.findById(output.getSellerId()));
		return ResponseEntity.ok(service.insert(o).toDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<OutputDTO> update(@PathVariable Integer id, @RequestBody OutputDTO output) {
		Output o = new Output(output, clientService.findById(output.getClientId()), sellerService.findById(output.getSellerId()));
		o.setId(id);
		return ResponseEntity.ok(service.update(o).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<OutputDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((output) -> output.toDTO()).toList());
	}
	
	@GetMapping("/client/{clientId}")
	public ResponseEntity<List<OutputDTO>> findByClient(@PathVariable Integer clientId) {
		List<Output> lista = service.findByClient(clientService.findById(clientId));
		return ResponseEntity.ok(lista.stream().map((Output) -> Output.toDTO()).toList());		
	}
	
	@GetMapping("/seller/{sellerId}")
	public ResponseEntity<List<OutputDTO>> findBySeller(@PathVariable Integer sellerId) {
		List<Output> lista = service.findBySeller(sellerService.findById(sellerId));
		return ResponseEntity.ok(lista.stream().map((Output) -> Output.toDTO()).toList());		
	}
}

package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.Seller;
import com.trier.gerenciamentoestoque.repositories.OutputRepository;
import com.trier.gerenciamentoestoque.services.OutputService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

@Service
public class OutputServiceImpl implements OutputService {

	@Autowired
	private OutputRepository repository;

	private void validateOutput(Output output) {
		if (output.getClient() == null) {
			throw new IntegrityViolation("Cliente não pode ser nulo");
		}
		if (output.getSeller() == null) {
			throw new IntegrityViolation("Vendedor não pode ser nulo");
		}
	}

	@Override
	public Output findById(Integer id) {
		if (id == 0) {
			return null;
		}
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("A saída %s não existe".formatted(id)));
	}

	@Override
	public Output insert(Output output) {
		validateOutput(output);
		return repository.save(output);
	}

	@Override
	public List<Output> listAll() {
		List<Output> lista = repository.findAll();
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhuma saída cadastrada");
		}
		return lista;
	}

	@Override
	public Output update(Output output) {
		validateOutput(output);
		return repository.save(output);
	}

	@Override
	public void delete(Integer id) {
		Output output = findById(id);
		repository.delete(output);
	}

	@Override
	public List<Output> findByClient(Client client) {
		List<Output> lista = repository.findByClient(client);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhuma saída encontrada para o cliente %s".formatted(client.getName()));
		}
		return lista;
	}

	@Override
	public List<Output> findBySeller(Seller seller) {
		List<Output> lista = repository.findBySeller(seller);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhuma saída encontrada para o vendedor %s (%s)".formatted(seller.getName(),
					seller.getRegistration()));
		}
		return lista;
	}
}
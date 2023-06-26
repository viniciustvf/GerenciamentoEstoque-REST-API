package com.trier.gerenciamentoestoque.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.repositories.ClientRepository;
import com.trier.gerenciamentoestoque.services.ClientService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;



@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository repository;
	
	private void validateClient(Client client) {
		if(client.getAge() == null) {
			throw new IntegrityViolation("Idade do cliente não pode ser nula");
		}
	}

	@Override
	public Client findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("O cliente %s não existe".formatted(id)));
	}

	@Override
	public Client insert(Client client) {
		validateClient(client);
		return repository.save(client);
	}

	@Override
	public List<Client> listAll() {
		List<Client> lista = repository.findAll();
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhuma categoria cadastrada");
		}
		return lista;
	}

	@Override
	public Client update(Client client) {
		validateClient(client);
		return repository.save(client);
	}

	@Override
	public void delete(Integer id) {
		Client client = findById(id);
		repository.delete(client);
	}
	
}	
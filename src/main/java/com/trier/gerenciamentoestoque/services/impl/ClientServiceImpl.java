package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;

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
			throw new IntegrityViolation("Idade não pode ser nula");
		}
		if(client.getCpf() == null) {
			throw new IntegrityViolation("CPF não pode ser nulo");
		}
		if(client.getName() == null) {
			throw new IntegrityViolation("Nome não pode ser nulo");
		}
		if(client.getNumber() == null) {
			throw new IntegrityViolation("Número inválido");
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
			throw new ObjectNotFound("Nenhum cliente cadastrado");
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

	@Override
	public List<Client> findByNameOrderByNameDesc(String name) {
		List<Client> lista = repository.findByNameOrderByNameDesc(name);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum nome %s encontrado".formatted(name));
		}
		return lista;
	}

	@Override
	public Client findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	public List<Client> findByAge(Integer age) {
		List<Client> lista = repository.findByAge(age);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum cliente encontrado com %s anos".formatted(age));
		}
		return lista;
	}

	@Override
	public List<Client> findByAgeBetween(Integer ageI, Integer ageF) {
		List<Client> lista = repository.findByAgeBetween(ageI, ageF);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum cliente encontrado com idades entre %s e %s anos".formatted(ageI, ageF));
		}
		return lista;
	}

	@Override
	public Client findByNumber(Long number) {
		return repository.findByNumber(number);
	}
	
}	
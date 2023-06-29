package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Seller;
import com.trier.gerenciamentoestoque.repositories.SellerRepository;
import com.trier.gerenciamentoestoque.services.SellerService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerRepository repository;

	private void validateSeller(Seller seller) {
		if (seller.getName() == null) {
			throw new IntegrityViolation("O nome do vendedor(a) não pode ser nulo");
		}
		if (seller.getRegistration() == null) {
			throw new IntegrityViolation("A matricula do vendedor náo pode ser nula");
		}
	}

	@Override
	public Seller findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("O vendedor %s não existe".formatted(id)));
	}

	@Override
	public Seller insert(Seller seller) {
		validateSeller(seller);
		return repository.save(seller);
	}

	@Override
	public List<Seller> listAll() {
		List<Seller> lista = repository.findAll();
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum vendedor cadastrado");
		}
		return lista;
	}

	@Override
	public Seller update(Seller seller) {
		findById(seller.getId());
		validateSeller(seller);
		return repository.save(seller);
	}

	@Override
	public void delete(Integer id) {
		Seller seller = findById(id);
		repository.delete(seller);
	}

	@Override
	public List<Seller> findByNameStartingWithIgnoreCaseOrderByNameDesc(String name) {
		List<Seller> lista = repository.findByNameStartingWithIgnoreCaseOrderByNameDesc(name);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum vendedor com nome %s cadastrado".formatted(name));
		}
		return lista;
	}

	@Override
	public Optional<Seller> findByRegistration(String registration) {
		return Optional.ofNullable(repository.findByRegistration(registration).orElseThrow(
				() -> new ObjectNotFound("Vendedor não encontrado com a matrícula %s".formatted(registration))));
	}
}

package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Supplier;
import com.trier.gerenciamentoestoque.repositories.SupplierRepository;
import com.trier.gerenciamentoestoque.services.SupplierService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository repository;
	
	private void validateSupplier(Supplier supplier) {
		if(supplier.getName() == null) {
			throw new IntegrityViolation("O nome do fornecedor(a) não pode ser nulo");
		}
		if(supplier.getCnpj() == null) {
			throw new IntegrityViolation("O CNPJ do fornecedor náo pode ser nulo");
		}
	}

	@Override
	public Supplier findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("O fornecedor %s não existe".formatted(id)));
	}

	@Override
	public Supplier insert(Supplier supplier) {
		validateSupplier(supplier);
		return repository.save(supplier);
	}

	@Override
	public List<Supplier> listAll() {
		List<Supplier> lista = repository.findAll();
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhum fornecedor cadastrado");
		}
		return lista;
	}

	@Override
	public Supplier update(Supplier supplier) {
		findById(supplier.getId());
		validateSupplier(supplier);
		return repository.save(supplier);
	}

	@Override
	public void delete(Integer id) {
		Supplier supplier = findById(id);
		repository.delete(supplier);
	}

	@Override
	public List<Supplier> findByName(String name) {
		List<Supplier> lista = repository.findByName(name);
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhum fornecedor cadastrado com o nome %s".formatted(name));
		}
		return lista;
	}

	@Override
	public Optional<Supplier> findByCnpj(String cnpj) {
		return Optional.ofNullable(repository.findByCnpj(cnpj).orElseThrow(() -> new ObjectNotFound("Fornecedor não encontrado com o CNPJ %s".formatted(cnpj))));	
	}
}












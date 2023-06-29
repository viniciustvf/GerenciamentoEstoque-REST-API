package com.trier.gerenciamentoestoque.services;

import java.util.List;
import java.util.Optional;

import com.trier.gerenciamentoestoque.models.Seller;

public interface SellerService {

	Seller findById(Integer id);

	Seller insert(Seller seller);

	List<Seller> listAll();

	Seller update(Seller seller);

	void delete(Integer id);

	List<Seller> findByNameStartingWithIgnoreCaseOrderByNameDesc(String name);

	Optional<Seller> findByRegistration(String registration);

}

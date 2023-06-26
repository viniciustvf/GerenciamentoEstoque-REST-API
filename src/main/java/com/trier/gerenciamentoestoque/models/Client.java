package com.trier.gerenciamentoestoque.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity(name="client")
public class Client {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_client")
	private Integer id;
	
	@Column(name = "name_client")
	private String description;
	
	@Column(name = "cpf_client", unique = true)
	@Size(max = 11)
	private String cpf;
	
	@Column(name = "age_client")
	@Min(value = 0, message = "A idade deve ser maior ou igual a 0.")
	@Max(value = 150, message = "A idade deve ser menor ou igual a 150.")
	private Integer age;
	
	@Column(name = "number_client", unique = true)
	@Size(max = 11)
	private String number;

}
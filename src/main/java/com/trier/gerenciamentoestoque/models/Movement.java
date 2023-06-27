package com.trier.gerenciamentoestoque.models;


import java.time.ZonedDateTime;

import com.trier.gerenciamentoestoque.models.enums.MovementType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
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
@Entity(name = "movement")
public class Movement {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movement")
	private Integer id;
	
	@Column(name = "datetime_movement")
	private ZonedDateTime dateTime;
	
    @Column(name = "type_movement")
    private MovementType movementType;
    
    @ManyToOne
	private Entry entry;
	
    @ManyToOne
	private Output output;

	
	//@Transient
	//public MovementType getMovementType() {
	 //   if (entry == null) {
	 //       return MovementType.OUTPUT;
	//    } else if (output == null) {
	//        return MovementType.ENTRY;
	//    } else {
	        // Lógica adicional, caso ambos entry e output sejam não nulos
	  //  }
	//}

}
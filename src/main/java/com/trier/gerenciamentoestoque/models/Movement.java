package com.trier.gerenciamentoestoque.models;


import java.time.ZonedDateTime;

import com.trier.gerenciamentoestoque.models.dto.MovementDTO;
import com.trier.gerenciamentoestoque.models.enums.MovementType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.trier.gerenciamentoestoque.utils.DateUtils;
import com.trier.gerenciamentoestoque.utils.EnumUtils;

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
	
    @Column(name = "type_movement", updatable = false)
    private MovementType movementType;
    
    @ManyToOne
	private Entry entry;
	
    @ManyToOne
	private Output output;

    public Movement (MovementDTO dto, Entry entry, Output output) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getDateTime()), EnumUtils.strMovementTypeToEnum(dto.getMovementTypeString()), entry, output); 
	}
	
	public MovementDTO toDTO() {
	    return new MovementDTO(getId(), DateUtils.zonedDateTimeToStr(dateTime), movementType.getDescription(), entry.getId(), output.getId()); 
	}

}
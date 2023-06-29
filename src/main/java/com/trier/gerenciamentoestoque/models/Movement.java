package com.trier.gerenciamentoestoque.models;


import java.time.ZonedDateTime;

import com.trier.gerenciamentoestoque.models.dto.MovementDTO;
import com.trier.gerenciamentoestoque.models.enums.MovementType;
import com.trier.gerenciamentoestoque.utils.DateUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private MovementType movementType;
    
    @ManyToOne
	private Entry entry;
	
    @ManyToOne
	private Output output;

    public Movement (MovementDTO dto, Entry entry, Output output) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getDateTime()), entry, output); 
	}
	
	public MovementDTO toDTO() {
	    return new MovementDTO(getId(), DateUtils.zonedDateTimeToStr(dateTime), movementType.name(), entry.getId(), output.getId()); 
	}

	public Movement(Integer id, ZonedDateTime dateTime, Entry entry, Output output) {
		this.id = id;
		this.dateTime = dateTime;
		this.entry = entry;
		this.output = output;
	}

	public Movement(MovementType type) {
		this.movementType = type;
	}
}
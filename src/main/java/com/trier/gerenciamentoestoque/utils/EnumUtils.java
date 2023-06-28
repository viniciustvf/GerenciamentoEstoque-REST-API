package com.trier.gerenciamentoestoque.utils;

import com.trier.gerenciamentoestoque.models.enums.MovementType;

public class EnumUtils {

	public static MovementType strMovementTypeToEnum(String movementTypeString) {
	    for (MovementType type : MovementType.values()) {
	        if (type.name().equalsIgnoreCase(movementTypeString)) {
	            return type;
	        }
	    }
		return null;
	}
}
package com.trier.gerenciamentoestoque.models.enums;

public enum MovementType {
    
	ENTRY("Entry"),
    OUTPUT("Output");

    private final String description;

    MovementType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

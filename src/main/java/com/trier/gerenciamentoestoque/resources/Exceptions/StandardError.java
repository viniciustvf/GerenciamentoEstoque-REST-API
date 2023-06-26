package com.trier.gerenciamentoestoque.resources.Exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StandardError {

	private LocalDateTime time;
	private Integer status;
	private String error;
	private String url;
	
}

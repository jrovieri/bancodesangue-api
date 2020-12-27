package br.com.wkconsultoria.bancodesangue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class QueryResultDTO {

	private String text;
	
	private double value;
}

package br.com.wkconsultoria.bancodesangue.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TokenDTO {

	private String username;
	
	private String token;
	
	private List<String> roles;
}

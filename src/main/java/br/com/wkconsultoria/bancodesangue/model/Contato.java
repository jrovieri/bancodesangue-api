package br.com.wkconsultoria.bancodesangue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Contato {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String endereco;
	
	@Column
	private String numero;
	
	@Column
	private String bairro;
	
	@Column
	private String cidade;
	
	@Column
	private String estado;
	
	@Column
	private String cep;
	
	@Column
	@JsonAlias("telefone_fixo")
	private String telefoneFixo;
	
	@Column
	private String celular;
}

package br.com.wkconsultoria.bancodesangue.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Candidato {

	@Id @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String cpf;
	
	@Column
	private String rg;
	
	@Column
	private LocalDate dataNascimento;
	
	@Column
	private String sexo;
	
	@Column
	private String nomeMae;
	
	@Column
	private String nomePai;
	
	@Column
	private String email;
	
	@Column
	private int altura;
	
	@Column
	private int peso;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contato_id", referencedColumnName = "id")
	private Contato contato;
	
	@ManyToOne
	@JoinColumn(name = "tipo_sanguineo_id", nullable = false)
	private TipoSanguineo tipoSanguineo;
}

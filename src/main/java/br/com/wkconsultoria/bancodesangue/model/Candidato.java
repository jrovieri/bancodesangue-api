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

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.wkconsultoria.bancodesangue.serializer.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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
	@JsonAlias("data_nasc")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dataNascimento;
	
	@Column
	private String sexo;
	
	@Column
	@JsonAlias("mae")
	private String nomeMae;
	
	@Column
	@JsonAlias("pai")
	private String nomePai;
	
	@Column
	private String email;
	
	@Column
	private float altura;
	
	@Column
	private int peso;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contato_id", referencedColumnName = "id")
	private Contato contato;
	
	@ManyToOne
	@JoinColumn(name = "tipo_sanguineo_id", nullable = false)
	@JsonAlias("tipo_sanguineo")
	@JsonManagedReference
	private TipoSanguineo tipoSanguineo;
	
	@Formula("ROUND(peso / (altura * altura), 0)")
	private int imc;
	
	@Formula("YEAR(NOW()) - YEAR(data_nascimento)")
	private int idade;
	
	@Formula("CASE WHEN (YEAR(NOW()) - YEAR(data_nascimento)) BETWEEN 0 AND 10 THEN '0-10' "
			+ "WHEN (YEAR(NOW()) - YEAR(data_nascimento)) BETWEEN 11 AND 20 THEN '11-20' "
			+ "WHEN (YEAR(NOW()) - YEAR(data_nascimento)) BETWEEN 21 AND 30 THEN '21-30' "
			+ "WHEN (YEAR(NOW()) - YEAR(data_nascimento)) BETWEEN 31 AND 40 THEN '31-40' "
			+ "WHEN (YEAR(NOW()) - YEAR(data_nascimento)) BETWEEN 41 AND 50 THEN '41-50' "
			+ "WHEN (YEAR(NOW()) - YEAR(data_nascimento)) BETWEEN 51 AND 60 THEN '51-60' "
			+ "WHEN (YEAR(NOW()) - YEAR(data_nascimento)) BETWEEN 61 AND 70 THEN '61-70' "
			+ "WHEN (YEAR(NOW()) - YEAR(data_nascimento)) BETWEEN 71 AND 80 THEN '71-80' "
			+ "ELSE '80+' END")
	private String faixaEtaria;
}

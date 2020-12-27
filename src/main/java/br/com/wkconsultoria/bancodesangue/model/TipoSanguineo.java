package br.com.wkconsultoria.bancodesangue.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@JsonIdentityInfo(property = "id",
	scope = Long.class,
	generator = ObjectIdGenerators.PropertyGenerator.class)
public class TipoSanguineo {

	@Id @Column
	private String id;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tipo_sanguineo_receptor", 
		joinColumns = { @JoinColumn(name = "tipo_sanguineo_id") },
		inverseJoinColumns = { @JoinColumn(name="tipo_sanguineo_receptor_id") }
	)
	@JsonIdentityReference(alwaysAsId = true)
	private List<TipoSanguineo> receptor;
}

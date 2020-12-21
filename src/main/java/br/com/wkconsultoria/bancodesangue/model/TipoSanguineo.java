package br.com.wkconsultoria.bancodesangue.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class TipoSanguineo {

	@Id @Column
	private String id;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tipo_sanguineo_doador", 
		joinColumns = { @JoinColumn(name = "tipo_sanguineo_id") },
		inverseJoinColumns = { @JoinColumn(name="tipo_sanguineo_doador_id") }
	)
	private List<TipoSanguineo> doaPara;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tipo_sanguineo_receptor", 
		joinColumns = { @JoinColumn(name = "tipo_sanguineo_id") },
		inverseJoinColumns = { @JoinColumn(name="tipo_sanguineo_receptor_id") }
	)
	private List<TipoSanguineo> recebeDe;
}
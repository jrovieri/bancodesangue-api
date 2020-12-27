package br.com.wkconsultoria.bancodesangue.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.wkconsultoria.bancodesangue.model.Candidato;

@Repository
public interface CandidatoRepository extends PagingAndSortingRepository<Candidato, Long> {

	Candidato findById(long id);
	
	List<Candidato> findAll();
}

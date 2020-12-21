package br.com.wkconsultoria.bancodesangue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.wkconsultoria.bancodesangue.model.TipoSanguineo;

@Repository
public interface TipoSanguineoRepository extends CrudRepository<TipoSanguineo, String> {

}

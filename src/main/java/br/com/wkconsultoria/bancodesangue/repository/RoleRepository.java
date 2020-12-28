package br.com.wkconsultoria.bancodesangue.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.wkconsultoria.bancodesangue.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	List<Role> findAll();
}

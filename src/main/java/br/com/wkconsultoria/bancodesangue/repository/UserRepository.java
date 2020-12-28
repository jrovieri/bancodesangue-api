package br.com.wkconsultoria.bancodesangue.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.wkconsultoria.bancodesangue.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
}

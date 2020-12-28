package br.com.wkconsultoria.bancodesangue.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.wkconsultoria.bancodesangue.model.User;

public interface UserService extends UserDetailsService {

	User save(User user);
}

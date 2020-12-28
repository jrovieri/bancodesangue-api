package br.com.wkconsultoria.bancodesangue.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.wkconsultoria.bancodesangue.model.User;
import br.com.wkconsultoria.bancodesangue.repository.UserRepository;
import br.com.wkconsultoria.bancodesangue.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user == null) 
			throw new UsernameNotFoundException("Usuário não encontrado");
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
				user.getPassword(), user.isEnabled(), true, true, true, getAuthority(user));
	}
	
	@Override
	public User save(User user) {
		User newUser = new User();
		newUser.setName(user.getName());
		newUser.setUsername(user.getUsername());
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
	    newUser.setEnabled(true);
	    newUser.setRoles(user.getRoles());
	    return userRepository.save(newUser);
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}
}

package br.com.wkconsultoria.bancodesangue.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.wkconsultoria.bancodesangue.model.Role;
import br.com.wkconsultoria.bancodesangue.model.User;
import br.com.wkconsultoria.bancodesangue.repository.RoleRepository;
import br.com.wkconsultoria.bancodesangue.repository.UserRepository;
import br.com.wkconsultoria.bancodesangue.service.UserService;

@Component
public class CommandLineStartupRunner implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		User admin = userRepository.findByUsername("admin");
		if (admin == null) {
			List<Role> roles = roleRepository.findAll();
			
			User user = new User();
			user.setName("admin");
			user.setUsername("admin");
			user.setPassword("admin");
			user.setEnabled(true);
			user.setRoles(roles);
			
			userService.save(user);
		}
	}
}

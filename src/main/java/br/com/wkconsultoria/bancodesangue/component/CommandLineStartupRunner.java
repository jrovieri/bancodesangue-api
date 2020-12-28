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
		List<Role> roles = roleRepository.findAll();
		
		User admin = userRepository.findByUsername("admin");
		if (admin == null) {	
			User userAdmin = new User();
			userAdmin.setName("admin");
			userAdmin.setUsername("admin");
			userAdmin.setPassword("admin");
			userAdmin.setEnabled(true);
			userAdmin.setRoles(roles);
			userService.save(userAdmin);
		}
		
		User _user = userRepository.findByUsername("user");
		if (_user == null) {
			roles.remove(0);
			User user = new User();
			user.setName("user");
			user.setUsername("user");
			user.setPassword("user");
			user.setEnabled(true);
			user.setRoles(roles);
			userService.save(user);
		}
	}
}

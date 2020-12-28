package br.com.wkconsultoria.bancodesangue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wkconsultoria.bancodesangue.component.JwtTokenProvider;
import br.com.wkconsultoria.bancodesangue.dto.LoginDTO;
import br.com.wkconsultoria.bancodesangue.model.User;
import br.com.wkconsultoria.bancodesangue.repository.UserRepository;
import br.com.wkconsultoria.bancodesangue.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("register")
	public ResponseEntity<?> register(@RequestBody User user) {
		User newUser = userService.save(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(newUser); 
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws Throwable {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDTO.getUsername(), loginDTO.getPassword()));
		
		String token = tokenProvider.generateToken(loginDTO.getUsername());
		User user = userRepository.findByUsername(loginDTO.getUsername());
		user.setToken(token);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
	 }
}

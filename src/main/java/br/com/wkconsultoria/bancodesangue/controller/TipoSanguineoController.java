package br.com.wkconsultoria.bancodesangue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tipo_sanguineo")
public class TipoSanguineoController {

	
	@GetMapping("{id}")
	public void getTipoSanguineoById() {
		
	}
}

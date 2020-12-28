package br.com.wkconsultoria.bancodesangue.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.wkconsultoria.bancodesangue.model.Candidato;
import br.com.wkconsultoria.bancodesangue.repository.CandidatoRepository;
import br.com.wkconsultoria.bancodesangue.serializer.CandidatoDeserializer;

@CrossOrigin
@RestController
@RequestMapping("api/candidatos")
public class CandidatoController {

	@Autowired
	private CandidatoRepository candidatoRepository;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "load", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> loadJsonData(@RequestParam MultipartFile file) throws IOException {		
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode rootNode = objectMapper.readTree(file.getInputStream()).deepCopy();
		
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Candidato.class, new CandidatoDeserializer());
		objectMapper.registerModule(module);
		
		for (JsonNode node: rootNode) {
			Candidato candidato = objectMapper.readValue(node.toString(), Candidato.class);
			candidatoRepository.save(candidato);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		Candidato candidato = candidatoRepository.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(candidato);
	}
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam int page, @RequestParam int size) {
		Page<Candidato> candidatos = candidatoRepository.findAll(PageRequest.of(page, size, Sort.by("nome")));
		return ResponseEntity.status(HttpStatus.OK).body(candidatos.getContent());
	}
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PatchMapping
	public ResponseEntity<?> updateById(@RequestBody Candidato candidato) {
		if (!candidatoRepository.existsById(candidato.getId()))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		Candidato candidatoAtualizado = candidatoRepository.save(candidato);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(candidatoAtualizado);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable long id) {
		if (!candidatoRepository.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		candidatoRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}

package br.com.wkconsultoria.bancodesangue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wkconsultoria.bancodesangue.dto.QueryResultDTO;
import br.com.wkconsultoria.bancodesangue.service.CandidatoService;

@CrossOrigin
@RestController
@RequestMapping("api/stats")
public class StatsController {

	@Autowired
	private CandidatoService candidatoService;
	
	@GetMapping(value = "estado")
	public ResponseEntity<?> getCountCandidatoPorEstado() {
		List<QueryResultDTO> candidatos = candidatoService.countCandidatoPorEstado();
		return ResponseEntity.status(HttpStatus.OK).body(candidatos);
	}
	
	@GetMapping("avg-imc")
	public ResponseEntity<?> getAvgImcPorFaixaEtaria() {
		List<QueryResultDTO> result = candidatoService.avgImcPorFaixaEtaria();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("obesos")
	public ResponseEntity<?> getPctObesosPorSexo() {
		List<QueryResultDTO> result = candidatoService.pctObesosPorSexo();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("avg-idade-sangue")
	public ResponseEntity<?> getAvgIdadePorTipoSanguineo() {
		List<QueryResultDTO> result = candidatoService.avgIdadePorTipoSanguineo();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("doadores")
	public ResponseEntity<?> getSumDoadoresPorTipoSanguineo() {
		List<QueryResultDTO> candidatos = candidatoService.sumDoadoresPorTipoSanguineo();
		return ResponseEntity.status(HttpStatus.OK).body(candidatos);
	}
}

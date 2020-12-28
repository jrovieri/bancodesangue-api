package br.com.wkconsultoria.bancodesangue.service;

import java.util.List;

import br.com.wkconsultoria.bancodesangue.dto.QueryResultDTO;

public interface StatsService {

	List<QueryResultDTO> countCandidatoPorEstado();
	
	List<QueryResultDTO> avgImcPorFaixaEtaria();
	
	List<QueryResultDTO> sumDoadoresPorTipoSanguineo();
	
	List<QueryResultDTO> pctObesosPorSexo();
	
	List<QueryResultDTO> avgIdadePorTipoSanguineo();
}

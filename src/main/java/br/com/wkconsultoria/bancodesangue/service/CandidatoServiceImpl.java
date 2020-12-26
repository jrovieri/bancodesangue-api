package br.com.wkconsultoria.bancodesangue.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wkconsultoria.bancodesangue.dto.QueryResultDTO;
import br.com.wkconsultoria.bancodesangue.model.Candidato;
import br.com.wkconsultoria.bancodesangue.model.Contato;
import br.com.wkconsultoria.bancodesangue.model.TipoSanguineo;

@Service
public class CandidatoServiceImpl implements CandidatoService {

	@Autowired
	private EntityManager em;
	
	/**
	 * Total de doadores por tipo sanguineo
	 */
	@Override
	public List<QueryResultDTO> sumDoadoresPorTipoSanguineo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QueryResultDTO> cq = cb.createQuery(QueryResultDTO.class);
		Root<Candidato> candidato = cq.from(Candidato.class);
		
		Join<Candidato, TipoSanguineo> tipoSanguineo = candidato.join("tipoSanguineo");
		Predicate peso = cb.greaterThan(candidato.get("peso"), 50);
		Predicate idade = cb.between(candidato.get("idade"), 16, 69);
		
		Join<TipoSanguineo, TipoSanguineo> tipoSanguineoReceptor = tipoSanguineo.join("receptor");
		
		cq.multiselect(
				tipoSanguineoReceptor.get("id").alias("id"), 
				cb.count(candidato.get("id")).as(Double.class).alias("total"))
			.where(cb.and(peso, idade))
			.groupBy(tipoSanguineoReceptor.get("id"))
			.orderBy(cb.asc(tipoSanguineoReceptor.get("id")));
		
		return em.createQuery(cq).getResultList();
		
	}

	/**
	 * Total de candidatos por estado
	 */
	@Override
	public List<QueryResultDTO> countCandidatoPorEstado() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QueryResultDTO> cq = cb.createQuery(QueryResultDTO.class);
		Root<Candidato> candidato = cq.from(Candidato.class);
		
		Join<Candidato, Contato> contato = candidato.join("contato");
		cq.multiselect(
				contato.get("estado"),
				cb.count(candidato.get("id")).as(Double.class).alias("total"))
			.groupBy(contato.get("estado"))
			.orderBy((cb.desc(contato.get("estado"))));
		
		return em.createQuery(cq).getResultList();
	}

	/**
	 * IMC Médio por Faixa Etária
	 */
	@Override
	public List<QueryResultDTO> avgImcPorFaixaEtaria() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QueryResultDTO> cq = cb.createQuery(QueryResultDTO.class);
		Root<Candidato> candidato = cq.from(Candidato.class);
		
		cq.multiselect(
				candidato.get("faixaEtaria"), 
				cb.avg(candidato.get("imc")).as(Double.class))
			.groupBy(candidato.get("faixaEtaria"))
			.orderBy(cb.desc(candidato.get("faixaEtaria")));
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Porcentagem de obesos por sexo
	 */
	@Override
	public List<QueryResultDTO> pctObesosPorSexo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QueryResultDTO> cq = cb.createQuery(QueryResultDTO.class);
		
		Root<Candidato> candidato = cq.from(Candidato.class);
		
		Subquery<Long> totalObesos = cq.subquery(Long.class);
		Root<Candidato> candidatosObesos = totalObesos.from(Candidato.class);
		Predicate imc = cb.greaterThan(candidatosObesos.get("imc"), 30);
		totalObesos.select(cb.count(candidatosObesos.get("id"))).where(imc);
		
		cq.multiselect(
				candidato.get("sexo"), 
				cb.prod(cb.quot(cb.count(candidato.get("id")), totalObesos), 100)
					.as(Double.class)
					.alias("total"))
			.where(cb.greaterThan(candidato.get("imc"), 30))
			.groupBy(candidato.get("sexo"))
			.orderBy(cb.desc(candidato.get("sexo")));
		
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Média de idade por tipo sanguineo
	 */
	@Override
	public List<QueryResultDTO> avgIdadePorTipoSanguineo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QueryResultDTO> cq = cb.createQuery(QueryResultDTO.class);
		Root<Candidato> candidato = cq.from(Candidato.class);
		
		Join<Candidato, TipoSanguineo> tipoSanguineo = candidato.join("tipoSanguineo");
		Predicate peso = cb.greaterThan(candidato.get("peso"), 50);
		Predicate idade = cb.between(candidato.get("idade"), 16, 69);
		
		cq.multiselect(
				tipoSanguineo.get("id").alias("tipo"), 
				cb.avg(candidato.get("idade")).as(Double.class).alias("media"))
			.where(cb.and(peso, idade))
			.groupBy(tipoSanguineo.get("id"))
			.orderBy(cb.asc(tipoSanguineo.get("id")));
		return em.createQuery(cq).getResultList();
	}

}

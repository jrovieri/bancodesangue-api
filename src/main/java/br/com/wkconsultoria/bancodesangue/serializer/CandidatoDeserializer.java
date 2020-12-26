package br.com.wkconsultoria.bancodesangue.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.wkconsultoria.bancodesangue.model.Candidato;

public class CandidatoDeserializer extends StdDeserializer<Candidato> {

	private static final long serialVersionUID = 1L;

	public CandidatoDeserializer() {
		this(null);
	}
	
	public CandidatoDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Candidato deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String[] fieldNames = { "endereco", "numero", "bairro", "cidade", "estado", "cep", "telefone_fixo", "celular" };
		
		JsonNode node = p.getCodec().readTree(p);
		ObjectNode nodeCandidato = node.deepCopy();
		ObjectNode nodeContato = objectMapper.createObjectNode();
		
		for (String fieldName: fieldNames) {
			String fieldValue = nodeCandidato.get(fieldName).asText();
			nodeContato.put(fieldName, fieldValue);
			nodeCandidato.remove(fieldName);
		}
		nodeCandidato.set("contato", nodeContato);
		
		// Tipo Sanguineo
		ObjectNode nodeTipoSanguineo = objectMapper.createObjectNode();
		nodeTipoSanguineo.put("id", nodeCandidato.get("tipo_sanguineo").asText());
		nodeCandidato.replace("tipo_sanguineo", nodeTipoSanguineo);
		
		Candidato candidato = objectMapper.treeToValue(nodeCandidato, Candidato.class);
		return candidato;
	}
}

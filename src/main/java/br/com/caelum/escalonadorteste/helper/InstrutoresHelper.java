package br.com.caelum.escalonadorteste.helper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.caelum.escalonadorteste.modelo.Instrutor;

public class InstrutoresHelper {

	public String carregaRecursoJSON(String nomeDoRecurso) {
		try {
			Path caminho = Paths.get(getClass().getClassLoader().getResource(nomeDoRecurso).toURI());

			Stream<String> linhas = Files.lines(caminho);
			String jsonInstrutores = linhas.collect(Collectors.joining("\n"));
			linhas.close();

			return jsonInstrutores;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Instrutor> constroiListaAPartirDeJSON(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Instrutor> instrutores = mapper.readValue(json, new TypeReference<List<Instrutor>>(){});
			return instrutores;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

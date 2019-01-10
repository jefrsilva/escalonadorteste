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

import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class TurmasHelper {

	public String carregaRecursoJSON(String nomeDoRecurso) {
		try {
			Path caminho = Paths.get(getClass().getClassLoader().getResource(nomeDoRecurso).toURI());

			Stream<String> linhas = Files.lines(caminho);
			String jsonTurmas = linhas.collect(Collectors.joining("\n"));
			linhas.close();

			return jsonTurmas;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Turma> constroiListaAPartirDeJSON(String json) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		try {
			List<Turma> turmas = mapper.readValue(json, new TypeReference<List<Turma>>() {
			});
			return turmas;
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

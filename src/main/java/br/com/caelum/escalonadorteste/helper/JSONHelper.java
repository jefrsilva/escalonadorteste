package br.com.caelum.escalonadorteste.helper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JSONHelper {

	public String carregaRecursoJSON(String nomeDoRecurso) {
		try {
			Path caminho = Paths.get(getClass().getClassLoader().getResource(nomeDoRecurso).toURI());

			Stream<String> linhas = Files.lines(caminho);
			String json = linhas.collect(Collectors.joining("\n"));
			linhas.close();

			return json;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

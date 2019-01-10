package br.com.caelum.escalonadorteste.helper;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.caelum.escalonadorteste.modelo.Curso;

public class CursosHelper {

	public List<Curso> constroiListaAPartirDeJSON(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Curso> cursos = mapper.readValue(json, new TypeReference<List<Curso>>() {
			});
			return cursos;
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

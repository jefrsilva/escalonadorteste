package br.com.caelum.escalonadorteste.cweb;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Periodo;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class TurmasCWebHelper {

	private Map<String, Curso> cursos;
	private Map<String, Instrutor> instrutores;

	public TurmasCWebHelper(List<Curso> listaDeCursos, List<Instrutor> listaDeInstrutores) {
		cursos = new HashMap<>();
		listaDeCursos.forEach(curso -> {
			cursos.put(curso.getCodigo(), curso);
		});

		instrutores = new HashMap<>();
		listaDeInstrutores.forEach(instrutor -> {
			instrutores.put(instrutor.getNome(), instrutor);
		});
	}

	public List<Turma> getTurmas(String json) {
		List<Turma> turmas = new ArrayList<>();

		List<TurmaCWebDTO> turmasCWeb = constroiListaAPartirDeJSON(json);
		turmasCWeb.forEach(turmaCWeb -> {
			Turma turma = converteParaTurmaEscalonador(turmaCWeb);
			turmas.add(turma);
		});

		return turmas;
	}

	private List<TurmaCWebDTO> constroiListaAPartirDeJSON(String json) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			List<TurmaCWebDTO> turmas = mapper.readValue(json, new TypeReference<List<TurmaCWebDTO>>() {
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

	private Turma converteParaTurmaEscalonador(TurmaCWebDTO turmaCWeb) {
		String codigoCurso = turmaCWeb.getCodigo();
		String nomeDoPeriodo = turmaCWeb.getPeriodo().split(" ")[0].toUpperCase();
		nomeDoPeriodo = nomeDoPeriodo.replaceAll("Ã", "A");
		nomeDoPeriodo = nomeDoPeriodo.replaceAll("Á", "A");
		Periodo periodo = Periodo.valueOf(Periodo.class, nomeDoPeriodo);
		List<LocalDate> diasDeAula = geraDiasDeAula(turmaCWeb.getDias());
		boolean ehViagem = turmaCWeb.isViagem();
		String instrutor = turmaCWeb.getInstrutor();
		Turma turma = new Turma(codigoCurso, periodo, diasDeAula, ehViagem, new ArrayList<>());
		if (instrutor != null) {
			if (instrutores.get(instrutor) == null) {
				System.out.println(instrutor);
			}
			turma.setInstrutor(instrutores.get(instrutor));
			turma.setInstrutorPlanejado(instrutores.get(instrutor));
			turma.setFixo(true);
		}
		return turma;
	}

	private List<LocalDate> geraDiasDeAula(List<String> diasEmTexto) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		List<LocalDate> diasDeAula = diasEmTexto.stream().map(diaEmTexto -> LocalDate.parse(diaEmTexto, formatter))
				.collect(Collectors.toList());
		return diasDeAula;
	}

}

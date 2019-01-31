package br.com.caelum.escalonadorteste.cweb;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Periodo;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class TurmasCWebHelper {

	private Set<Integer> turmasCriadas;
	private Map<String, Curso> cursos;

	public TurmasCWebHelper(List<Curso> listaDeCursos) {
		turmasCriadas = new HashSet<>();
		cursos = new HashMap<>();
		listaDeCursos.forEach(curso -> {
			cursos.put(curso.getCodigo(), curso);
		});
	}

	public List<Turma> getTurmas(String json) {
		List<Turma> turmas = new ArrayList<>();

		TurmasCWebDTO turmasCWeb = constroiListaAPartirDeJSON(json);
		turmasCWeb.getSemanas().forEach(semana -> {
			semana.getTurmasDaSemana().forEach(turmaCWeb -> {
				if (!turmasCriadas.contains(turmaCWeb.getId())) {
					turmasCriadas.add(turmaCWeb.getId());
					Turma turma = converteParaTurmaEscalonador(turmaCWeb);
					turmas.add(turma);
				}
			});
		});

		return turmas;
	}

	private TurmasCWebDTO constroiListaAPartirDeJSON(String json) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			TurmasCWebDTO turmas = mapper.readerFor(TurmasCWebDTO.class).withRootName("turmas").readValue(json);
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
		String codigoCurso = turmaCWeb.getCodigoCurso();
		String nomeDoPeriodo = turmaCWeb.getHorario().split(" ")[0].toUpperCase();
		Periodo periodo = Periodo.valueOf(Periodo.class, nomeDoPeriodo);
		List<LocalDate> diasDeAula = geraDiasDeAula(codigoCurso, periodo, turmaCWeb.getPrimeiroDia(),
				turmaCWeb.getUltimoDia());
		boolean ehViagem = false;
		if (turmaCWeb.getCidade() != null) {
			ehViagem = !turmaCWeb.getCidade().equals("Sao Paulo");	
		}
		return new Turma(codigoCurso, periodo, diasDeAula, ehViagem);
	}

	private List<LocalDate> geraDiasDeAula(String codigoCurso, Periodo periodo, LocalDate primeiroDia,
			LocalDate ultimoDia) {
		List<LocalDate> diasDeAula = new ArrayList<>();
		System.out.println(codigoCurso);
		int cargaHoraria = cursos.get(codigoCurso).getCargaHoraria();
		LocalDate diaDaAula = LocalDate.from(primeiroDia);

		if (periodo == Periodo.INTEGRAL) {
			int diasACriar = (int) Math.ceil((double) cargaHoraria / periodo.getCargaDiaria());
			while (diasACriar > 0) {
				diasDeAula.add(diaDaAula);
				diaDaAula = diaDaAula.plusDays(1);
				diasACriar--;
			}
		} else if (periodo == Periodo.SABADO) {
			int diasACriar = (int) Math.ceil((double) cargaHoraria / periodo.getCargaDiaria());
			while (diasACriar > 0) {
				diasDeAula.add(diaDaAula);
				diaDaAula = diaDaAula.plusDays(7);
				diasACriar--;
			}
		} else if (periodo == Periodo.MANHA || periodo == Periodo.TARDE || periodo == Periodo.NOTURNO) {
			int diasACriar = (int) Math.ceil((double) cargaHoraria / periodo.getCargaDiaria());
			int maximoDeDiasPorSemana = 5;
			if (diasACriar % 5 != 0) {
				maximoDeDiasPorSemana = 4;
			}
			int diasNaSemana = 0;
			while (diasACriar > 0) {
				diasDeAula.add(diaDaAula);
				diaDaAula = diaDaAula.plusDays(1);
				diasACriar--;
				diasNaSemana++;
				if (diasNaSemana >= maximoDeDiasPorSemana) {
					diaDaAula = primeiroDia.plusWeeks(1);
					diasNaSemana = 0;
				}
			}
		}

		return diasDeAula;
	}

}

package br.com.caelum.escalonadorteste;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.test.impl.score.buildin.hardsoft.HardSoftScoreVerifier;

import br.com.caelum.escalonadorteste.alocacao.AlocacaoDeInstrutores;
import br.com.caelum.escalonadorteste.helper.CursosHelper;
import br.com.caelum.escalonadorteste.helper.InstrutoresHelper;
import br.com.caelum.escalonadorteste.helper.JSONHelper;
import br.com.caelum.escalonadorteste.helper.TurmasHelper;
import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Intervalo;
import br.com.caelum.escalonadorteste.modelo.Periodo;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class AlocacaoDeInstrutoresScoreTest {

	private HardSoftScoreVerifier<AlocacaoDeInstrutores> verificador = new HardSoftScoreVerifier<>(
			SolverFactory.createFromXmlResource("escalonador/conf/alocacaoSolverConfig.xml"));

	@Test
	public void instrutorNaoSabeDarOCurso() {
		JSONHelper jsonHelper = new JSONHelper();
		String jsonInstrutores = jsonHelper.carregaRecursoJSON("instrutores_instrutorNaoSabeDarOCurso.json");
		String jsonCursos = jsonHelper.carregaRecursoJSON("cursos_instrutorNaoSabeDarOCurso.json");
		String jsonTurmas = jsonHelper.carregaRecursoJSON("turmas_instrutorNaoSabeDarOCurso.json");

		InstrutoresHelper instrutoresHelper = new InstrutoresHelper();
		List<Instrutor> instrutores = instrutoresHelper.constroiListaAPartirDeJSON(jsonInstrutores);

		CursosHelper cursosHelper = new CursosHelper();
		List<Curso> cursos = cursosHelper.constroiListaAPartirDeJSON(jsonCursos);

		TurmasHelper turmasHelper = new TurmasHelper();
		List<Turma> turmas = turmasHelper.constroiListaAPartirDeJSON(jsonTurmas);

		String nomeDaRegra = "não sabe dar o curso";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor que sabe dar o curso
		turmas.get(0).setInstrutor(instrutores.get(0));
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor que não sabe dar o curso
		turmas.get(0).setInstrutor(instrutores.get(1));
		verificador.assertHardWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void instrutorNaoPodeDarAulaNoPeriodo() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.NOTURNO),
				Arrays.asList(Periodo.NOTURNO), false, Collections.emptyList(), false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		turmas.add(turma1);

		String nomeDaRegra = "não consegue dar aula no período";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor que consegue dar aula no período da turma
		turma1.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor que não consegue dar aula no período da turma
		turma1.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void instrutorNaoPrefereDarAulaNoPeriodo() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL, Periodo.NOTURNO),
				Arrays.asList(Periodo.INTEGRAL, Periodo.NOTURNO), false, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL, Periodo.NOTURNO),
				Arrays.asList(Periodo.NOTURNO), false, Collections.emptyList(), false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		Turma turma2 = new Turma("Curso 1", Periodo.NOTURNO,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		turmas.add(turma1);
		turmas.add(turma2);

		String nomeDaRegra = "pegou aula em período fora dos preferenciais";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Cada instrutor pegou uma turma no período de preferência
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Instrutor 2 prefere não dar aula no período integral
		turma1.setInstrutor(instrutor2);
		turma2.setInstrutor(instrutor1);
		verificador.assertSoftWeight(nomeDaRegra, -20, alocacao);
	}

	@Test
	public void instrutorEstaIndisponivelNosDiasDaTurma() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false,
				Arrays.asList(
						new Intervalo(LocalDateTime.of(2018, 11, 27, 18, 0), LocalDateTime.of(2018, 11, 28, 8, 0))),
				false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.NOTURNO),
				Arrays.asList(Periodo.NOTURNO), false,
				Arrays.asList(
						new Intervalo(LocalDateTime.of(2018, 11, 27, 0, 0), LocalDateTime.of(2018, 11, 27, 23, 59))),
				false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		turmas.add(turma1);

		String nomeDaRegra = "não está disponível nos dias da turma";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor que está disponível nos dias da turma
		turma1.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor que não está disponível nos dias da turma
		turma1.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void instrutorPegouMuitasHorasDeAula() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor3 = new Instrutor("Instrutor C", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), true);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);
		instrutores.add(instrutor3);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		Turma turma2 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 4), LocalDate.of(2018, 12, 5),
						LocalDate.of(2018, 12, 6), LocalDate.of(2018, 12, 7)),
				false, Collections.emptyList());
		turmas.add(turma1);
		turmas.add(turma2);

		String nomeDaRegra = "mais de X horas de aula";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Dois instrutores dividindo as aulas, carga horária <= 60 para ambos
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Um instrutor para todas aulas, carga horária > 60
		turma2.setInstrutor(instrutor1);
		verificador.assertSoftWeight(nomeDaRegra, -20, alocacao);

		// Um instrutor externo para todas aulas, carga horária > 60
		turma1.setInstrutor(instrutor3);
		turma2.setInstrutor(instrutor3);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
	}

	@Test
	public void instrutorPegouDuasTurmasSimultaneas() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"),
				Arrays.asList(Periodo.MANHA, Periodo.TARDE, Periodo.INTEGRAL), Arrays.asList(Periodo.MANHA, Periodo.TARDE, Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		Turma turma2 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		Turma turma3 = new Turma("Curso 1", Periodo.MANHA,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		Turma turma4 = new Turma("Curso 1", Periodo.TARDE,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());

		turmas.add(turma1);
		turmas.add(turma2);

		String nomeDaRegra = "data de um curso conflita com outro";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);
		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutores diferentes para cada turma, sem conflito
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Mesmo instrutor para turmas no mesmo período, tem conflito
		turma2.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, -20, alocacao);

		// Mesmo instrutor para turmas em períodos sem sobreposição (manhã, tarde), sem
		// conflito
		turmas.remove(turma1);
		turmas.remove(turma2);
		turmas.add(turma3);
		turmas.add(turma4);
		turma3.setInstrutor(instrutor1);
		turma4.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Mesmo instrutor para turmas em períodos com sobreposição (manhã, integral),
		// com conflito
		turmas.remove(turma4);
		turmas.add(turma1);
		turma1.setInstrutor(instrutor1);
		turma3.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, -20, alocacao);
	}

	@Test
	public void instrutorPegouMuitasTurmasSeguidas() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor3 = new Instrutor("Instrutor C", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), true);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);
		instrutores.add(instrutor3);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		Turma turma2 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 4), LocalDate.of(2018, 12, 5),
						LocalDate.of(2018, 12, 6), LocalDate.of(2018, 12, 7)),
				false, Collections.emptyList());
		turmas.add(turma1);
		turmas.add(turma2);

		String nomeDaRegra = "mais de X turmas seguidas";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 80, 1);

		// Alocação vazia
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Dois instrutores dividindo as turmas, cada um dá 1 turma no máximo
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Um único instrutor pegou as 2 turmas (> limite de 1 turma seguida)
		turma2.setInstrutor(instrutor1);
		verificador.assertSoftWeight(nomeDaRegra, -10, alocacao);

		// Um único instrutor externo pegou as 2 turmas (> limite de 1 turma seguida)
		turma1.setInstrutor(instrutor3);
		turma2.setInstrutor(instrutor3);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
	}

	@Test
	public void instrutorPlanejadoParaOCursoFoiTrocado() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		turmas.add(turma1);

		String nomeDaRegra = "instrutor planejado foi trocado";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 80, 1);

		// Alocação vazia
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Instrutor planejado foi o instrutor alocado
		turma1.setInstrutorPlanejado(instrutor1);
		turma1.setInstrutor(instrutor1);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Instrutor planejado foi trocado
		turma1.setInstrutor(instrutor2);
		verificador.assertSoftWeight(nomeDaRegra, -1, alocacao);
	}

	@Test
	public void instrutorNaoEstaDisponivelParaViagem() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), true, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				true, Collections.emptyList());
		turmas.add(turma1);

		String nomeDaRegra = "instrutor não está disponível para viagem";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 80, 1);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor alocado pode viajar
		turma1.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor alocado não pode viajar
		turma1.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void instrutorNaoPodeDarIntegralENoturnoSimultaneamente() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.NOTURNO),
				Arrays.asList(Periodo.NOTURNO), false, Collections.emptyList(), false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		Turma turma2 = new Turma("Curso 1", Periodo.NOTURNO,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 3),
						LocalDate.of(2018, 12, 4), LocalDate.of(2018, 12, 5), LocalDate.of(2018, 12, 6),
						LocalDate.of(2018, 12, 7)),
				false, Collections.emptyList());
		turmas.add(turma1);
		turmas.add(turma2);

		String nomeDaRegra = "pegou integral e noturno simultaneamente";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Um instrutor diferente para cada período
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Mesmo instrutor para os dois períodos
		turma2.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void aulasConflitamComDiasDeViagens() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL, Periodo.SABADO),
				Arrays.asList(Periodo.INTEGRAL, Periodo.SABADO), true, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL, Periodo.SABADO),
				Arrays.asList(Periodo.INTEGRAL, Periodo.SABADO), true, Collections.emptyList(), false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 20));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28)),
				true, Collections.emptyList());
		Turma turma2 = new Turma("Curso 1", Periodo.SABADO,
				Arrays.asList(LocalDate.of(2018, 12, 1), LocalDate.of(2018, 12, 8), LocalDate.of(2018, 12, 15),
						LocalDate.of(2018, 12, 22), LocalDate.of(2018, 11, 29)),
				false, Collections.emptyList());
		turmas.add(turma1);
		turmas.add(turma2);

		String nomeDaRegra = "aula de sábado durante ou logo após viagem";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Um instrutor viaja e o outro dá a aula de sábado
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// O mesmo instrutor viaja e na volta já tem aula de sábado
		turma2.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void instrutorEstaRestritoParaATurma() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), true, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), true, Collections.emptyList(), false);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 20));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28)),
				true, Arrays.asList("Instrutor B"));
		turmas.add(turma1);

		String nomeDaRegra = "instrutor está restrito para a turma";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Sem restrição para o instrutor da turma
		turma1.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor está restrito para a turma
		turma1.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void horasDeAulaNaoForamDistribuidasIgualmente() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor3 = new Instrutor("Instrutor C", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), false);
		Instrutor instrutor4 = new Instrutor("Instrutor D", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL),
				Arrays.asList(Periodo.INTEGRAL), false, Collections.emptyList(), true);

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);
		instrutores.add(instrutor3);
		instrutores.add(instrutor4);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)),
				false, Collections.emptyList());
		Turma turma2 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 4), LocalDate.of(2018, 12, 5),
						LocalDate.of(2018, 12, 6), LocalDate.of(2018, 12, 7)),
				false, Collections.emptyList());
		Turma turma3 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 12, 10), LocalDate.of(2018, 12, 11), LocalDate.of(2018, 12, 12),
						LocalDate.of(2018, 12, 13), LocalDate.of(2018, 12, 14)),
				false, Collections.emptyList());
		turmas.add(turma1);
		turmas.add(turma2);
		turmas.add(turma3);

		String nomeDaRegra = "horas de aulas não foram distribuídas igualmente";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 120, 3);

		// Alocação vazia
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Dois instrutores dividindo as aulas, carga distribuída igualmente
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		turma3.setInstrutor(instrutor3);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);

		// Um instrutor para todas aulas, 120 horas de diferença de carga horária
		turma2.setInstrutor(instrutor1);
		turma3.setInstrutor(instrutor1);
		verificador.assertSoftWeight(nomeDaRegra, -120, alocacao);

		// Um instrutor pega 80 horas, outro pega 40 horas e o último pega 0 horas. Aqui seriam 80 horas de diferença
		turma2.setInstrutor(instrutor1);
		turma3.setInstrutor(instrutor2);
		verificador.assertSoftWeight(nomeDaRegra, -80, alocacao);
		
		// Um instrutor externo para todas aulas, sem problemas
		turma1.setInstrutor(instrutor4);
		turma2.setInstrutor(instrutor4);
		turma3.setInstrutor(instrutor4);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
	}
}

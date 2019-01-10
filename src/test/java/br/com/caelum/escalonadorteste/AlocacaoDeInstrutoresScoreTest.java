package br.com.caelum.escalonadorteste;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.test.impl.score.buildin.hardsoft.HardSoftScoreVerifier;

import br.com.caelum.escalonadorteste.alocacao.AlocacaoDeInstrutores;
import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Periodo;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class AlocacaoDeInstrutoresScoreTest {

	private HardSoftScoreVerifier<AlocacaoDeInstrutores> verificador = new HardSoftScoreVerifier<>(
			SolverFactory.createFromXmlResource("br/com/caelum/escalonadorteste/conf/alocacaoSolverConfig.xml"));

	@Test
	public void instrutorNaoSabeDarOCurso() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 2"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
		turmas.add(turma1);

		String nomeDaRegra = "não sabe dar o curso";
		AlocacaoDeInstrutores alocacao = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 60, 3);

		// Alocação vazia
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor que sabe dar o curso
		turma1.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);

		// Instrutor que não sabe dar o curso
		turma1.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void instrutorNaoPodeDarAulaNoPeriodo() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.NOTURNO));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
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
	public void instrutorPegouMuitasHorasDeAula() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
		Turma turma2 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 4), LocalDate.of(2018, 12, 5),
						LocalDate.of(2018, 12, 6), LocalDate.of(2018, 12, 7)));
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
		verificador.assertSoftWeight(nomeDaRegra, -10, alocacao);
	}

	@Test
	public void instrutorPegouDuasTurmasSimultaneas() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"),
				Arrays.asList(Periodo.MANHA, Periodo.TARDE, Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
		Turma turma2 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
		Turma turma3 = new Turma("Curso 1", Periodo.MANHA,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
		Turma turma4 = new Turma("Curso 1", Periodo.TARDE,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));

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
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
		Turma turma2 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 4), LocalDate.of(2018, 12, 5),
						LocalDate.of(2018, 12, 6), LocalDate.of(2018, 12, 7)));
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
	}

	@Test
	public void instrutorPlanejadoParaOCursoFoiTrocado() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Curso> cursos = Arrays.asList(new Curso("Curso 1", 40));

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", Periodo.INTEGRAL,
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28),
						LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
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

}

package br.com.caelum.escalonadorteste;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.test.impl.score.buildin.hardsoft.HardSoftScoreVerifier;

import br.com.caelum.escalonadorteste.modelo.Alocacao;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Periodo;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class AlocacaoScoreTest {

	private HardSoftScoreVerifier<Alocacao> verificador = new HardSoftScoreVerifier<>(
			SolverFactory.createFromXmlResource("br/com/caelum/escalonadorteste/conf/alocacaoSolverConfig.xml"));

	@Test
	public void instrutorNaoSabeDarOCurso() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 2"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 30), 40,
				Periodo.INTEGRAL);
		turmas.add(turma1);
		
		String nomeDaRegra = "não sabe dar o curso";
		Alocacao alocacao = new Alocacao(turmas, instrutores, 60, 3);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);
		
		turma1.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);
		
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

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 30), 40,
				Periodo.INTEGRAL);
		turmas.add(turma1);
		
		String nomeDaRegra = "não consegue dar aula no período";
		Alocacao alocacao = new Alocacao(turmas, instrutores, 60, 3);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);
		
		turma1.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);
		
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

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 30), 40,
				Periodo.INTEGRAL);
		Turma turma2 = new Turma("Curso 1", LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 7), 40,
				Periodo.INTEGRAL);
		turmas.add(turma1);
		turmas.add(turma2);
		
		String nomeDaRegra = "mais de X horas de aula";
		Alocacao alocacao = new Alocacao(turmas, instrutores, 60, 3);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
		
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
		
		turma2.setInstrutor(instrutor1);
		verificador.assertSoftWeight(nomeDaRegra, -10, alocacao);
	}
	
	@Test
	public void instrutorPegouDuasTurmasSimultaneas() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 30), 40,
				Periodo.INTEGRAL);
		Turma turma2 = new Turma("Curso 1", LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 30), 40,
				Periodo.INTEGRAL);
		turmas.add(turma1);
		turmas.add(turma2);
		
		String nomeDaRegra = "data de um curso conflita com outro";
		Alocacao alocacao = new Alocacao(turmas, instrutores, 60, 3);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);
		
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		verificador.assertHardWeight(nomeDaRegra, 0, alocacao);
		
		turma2.setInstrutor(instrutor1);
		verificador.assertHardWeight(nomeDaRegra, -20, alocacao);
	}
	
	@Test
	public void instrutorPegouMuitasTurmasSeguidas() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 30), 40,
				Periodo.INTEGRAL);
		Turma turma2 = new Turma("Curso 1", LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 7), 40,
				Periodo.INTEGRAL);
		turmas.add(turma1);
		turmas.add(turma2);
		
		String nomeDaRegra = "mais de X turmas seguidas";
		Alocacao alocacao = new Alocacao(turmas, instrutores, 80, 1);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
		
		turma1.setInstrutor(instrutor1);
		turma2.setInstrutor(instrutor2);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
		
		turma2.setInstrutor(instrutor1);
		verificador.assertSoftWeight(nomeDaRegra, -10, alocacao);
	}
	
	@Test
	public void instrutorFixoDaTurmaFoiTrocado() {
		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1"), Arrays.asList(Periodo.INTEGRAL));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 30), 40,
				Periodo.INTEGRAL);
		turmas.add(turma1);
		
		String nomeDaRegra = "instrutor fixado foi trocado";
		Alocacao alocacao = new Alocacao(turmas, instrutores, 80, 1);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
		
		turma1.setInstrutor(instrutor1);
		turma1.setInstrutorFixo(instrutor1);
		verificador.assertSoftWeight(nomeDaRegra, 0, alocacao);
		
		turma1.setInstrutor(instrutor2);
		verificador.assertSoftWeight(nomeDaRegra, -1000, alocacao);
	}
	
}

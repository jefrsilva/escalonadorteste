package br.com.caelum.escalonadorteste;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import br.com.caelum.escalonadorteste.modelo.Alocacao;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Periodo;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class Escalonador {
	public static void main(String[] args) {
		SolverFactory<Alocacao> solverFactory = SolverFactory
				.createFromXmlResource("br/com/caelum/escalonadorteste/conf/alocacaoSolverConfig.xml");

		Solver<Alocacao> solver = solverFactory.buildSolver();

		Instrutor instrutor1 = new Instrutor("Instrutor A", Arrays.asList("Curso 1", "Curso 2"),
				Arrays.asList(Periodo.INTEGRAL));
		Instrutor instrutor2 = new Instrutor("Instrutor B", Arrays.asList("Curso 1", "Curso 2"),
				Arrays.asList(Periodo.INTEGRAL, Periodo.NOTURNO));
		Instrutor instrutor3 = new Instrutor("Instrutor C", Arrays.asList("Curso 1", "Curso 2"),
				Arrays.asList(Periodo.INTEGRAL, Periodo.NOTURNO));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(instrutor1);
		instrutores.add(instrutor2);
		instrutores.add(instrutor3);

		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma("Curso 1", LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 30), 40,
				Periodo.INTEGRAL);
		turma1.setInstrutorFixo(instrutor2);
		Turma turma2 = new Turma("Curso 1", LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 7), 40, Periodo.INTEGRAL);
		turma2.setInstrutorFixo(instrutor1);
		Turma turma3 = new Turma("Curso 1", LocalDate.of(2018, 12, 10), LocalDate.of(2018, 12, 14), 40,
				Periodo.INTEGRAL);
		// turma3.setInstrutorFixo(instrutor1);
		Turma turma4 = new Turma("Curso 1", LocalDate.of(2018, 12, 17), LocalDate.of(2018, 12, 21), 40,
				Periodo.INTEGRAL);
		turma4.setInstrutorFixo(instrutor2);

		turmas.add(turma1);
		turmas.add(turma2);
		turmas.add(turma3);
		turmas.add(turma4);

		Alocacao alocacaoNaoResolvida = new Alocacao(turmas, instrutores, 80, 1);
		Alocacao alocacao = solver.solve(alocacaoNaoResolvida);

		for (Turma turma : alocacao.getTurmas()) {
			System.out.println(turma);
		}
		System.out.println(alocacao.getScore());
	}
}

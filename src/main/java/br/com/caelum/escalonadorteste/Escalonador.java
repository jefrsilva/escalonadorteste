package br.com.caelum.escalonadorteste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import br.com.caelum.escalonadorteste.modelo.Alocacao;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class Escalonador {
	public static void main(String[] args) {
		SolverFactory<Alocacao> solverFactory = SolverFactory
				.createFromXmlResource("br/com/caelum/escalonadorteste/alocacaoSolverConfig.xml");
		Solver<Alocacao> solver = solverFactory.buildSolver();

		List<Turma> turmas = new ArrayList<>();
		turmas.add(new Turma("Curso 1"));
		turmas.add(new Turma("Curso 2"));
		turmas.add(new Turma("Curso 3"));
		turmas.add(new Turma("Curso 4"));
		turmas.add(new Turma("Curso 5"));
		turmas.add(new Turma("Curso 6"));

		List<Instrutor> instrutores = new ArrayList<>();
		instrutores.add(new Instrutor("Instrutor A", Arrays.asList("Curso 1", "Curso 2", "Curso 4")));
		instrutores.add(new Instrutor("Instrutor B", Arrays.asList("Curso 1", "Curso 3", "Curso 6")));
		instrutores.add(new Instrutor("Instrutor C", Arrays.asList("Curso 1", "Curso 2", "Curso 3")));

		Alocacao alocacaoNaoResolvida = new Alocacao(turmas, instrutores);
		Alocacao alocacao = solver.solve(alocacaoNaoResolvida);

		for (Turma turma : alocacao.getTurmas()) {
			System.out.println(turma);
		}
		System.out.println(alocacao.getScore());
	}
}

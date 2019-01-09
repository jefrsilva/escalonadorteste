package br.com.caelum.escalonadorteste;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import br.com.caelum.escalonadorteste.alocacao.AlocacaoDeInstrutores;
import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Periodo;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class Escalonador {
	public static void main(String[] args) {
		SolverFactory<AlocacaoDeInstrutores> solverFactory = SolverFactory
				.createFromXmlResource("br/com/caelum/escalonadorteste/conf/alocacaoSolverConfig.xml");

		Solver<AlocacaoDeInstrutores> solver = solverFactory.buildSolver();

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

		Curso curso1 = new Curso("Curso 1", 40);
		
		List<Turma> turmas = new ArrayList<>();
		Turma turma1 = new Turma(curso1, Periodo.INTEGRAL, 
				Arrays.asList(LocalDate.of(2018, 11, 26), LocalDate.of(2018, 11, 27), LocalDate.of(2018, 11, 28), LocalDate.of(2018, 11, 29), LocalDate.of(2018, 11, 30)));
		turma1.setInstrutor(instrutor2);
		turma1.setInstrutorPlanejado(instrutor2);
		Turma turma2 = new Turma(curso1, Periodo.INTEGRAL, 
				Arrays.asList(LocalDate.of(2018, 12, 3), LocalDate.of(2018, 12, 4), LocalDate.of(2018, 12, 5), LocalDate.of(2018, 12, 6), LocalDate.of(2018, 12, 7)));
		turma2.setInstrutor(instrutor2);
		turma2.setInstrutorPlanejado(instrutor2);
		Turma turma3 = new Turma(curso1, Periodo.INTEGRAL, 
				Arrays.asList(LocalDate.of(2018, 12, 10), LocalDate.of(2018, 12, 11), LocalDate.of(2018, 12, 12), LocalDate.of(2018, 12, 13), LocalDate.of(2018, 12, 14)));
		Turma turma4 = new Turma(curso1, Periodo.INTEGRAL, 
				Arrays.asList(LocalDate.of(2018, 12, 17), LocalDate.of(2018, 12, 18), LocalDate.of(2018, 12, 19), LocalDate.of(2018, 12, 20), LocalDate.of(2018, 12, 21)));
		turma4.setInstrutor(instrutor1);
		turma4.setInstrutorPlanejado(instrutor1);
		Turma turma5 = new Turma(curso1, Periodo.INTEGRAL, 
				Arrays.asList(LocalDate.of(2018, 12, 24), LocalDate.of(2018, 12, 25), LocalDate.of(2018, 12, 26), LocalDate.of(2018, 12, 27), LocalDate.of(2018, 12, 28)));
		turma5.setInstrutor(instrutor1);
		turma5.setInstrutorPlanejado(instrutor1);

		turmas.add(turma1);
		turmas.add(turma2);
		turmas.add(turma3);
		turmas.add(turma4);
		turmas.add(turma5);

		AlocacaoDeInstrutores alocacaoNaoResolvida = new AlocacaoDeInstrutores(turmas, instrutores, 160, 2);
		AlocacaoDeInstrutores alocacao = solver.solve(alocacaoNaoResolvida);

		for (Turma turma : alocacao.getTurmas()) {
			System.out.println(turma);
		}
		System.out.println(alocacao.getScore());
		
		alocacao.getTurmas().get(2).setInstrutor(null);
		alocacao.getInstrutores().remove(instrutor3);
		alocacao = solver.solve(alocacao);

		System.out.println("\n\n\n\n\n\n\nInstrutor C ficou doente");
		for (Turma turma : alocacao.getTurmas()) {
			System.out.println(turma);
		}
		System.out.println(alocacao.getScore());
	}
}

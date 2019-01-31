package br.com.caelum.escalonadorteste;

import java.util.List;

import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import br.com.caelum.escalonadorteste.alocacao.AlocacaoDeInstrutores;
import br.com.caelum.escalonadorteste.cweb.TurmasCWebHelper;
import br.com.caelum.escalonadorteste.helper.CursosHelper;
import br.com.caelum.escalonadorteste.helper.InstrutoresHelper;
import br.com.caelum.escalonadorteste.helper.JSONHelper;
import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class Escalonador {

	private List<Turma> turmas;
	private List<Instrutor> instrutores;
	private List<Curso> cursos;
	private int cargaHorariaMaximaPorInstrutor;
	private int maximoDeTurmasSeguidasPorInstrutor;

	public Escalonador(List<Curso> cursos, List<Instrutor> instrutores, List<Turma> turmas,
			int cargaHorariaMaximaPorInstrutor, int maximoDeTurmasSeguidasPorInstrutor) {
		this.cursos = cursos;
		this.instrutores = instrutores;
		this.turmas = turmas;
		this.cargaHorariaMaximaPorInstrutor = cargaHorariaMaximaPorInstrutor;
		this.maximoDeTurmasSeguidasPorInstrutor = maximoDeTurmasSeguidasPorInstrutor;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public void setInstrutores(List<Instrutor> instrutores) {
		this.instrutores = instrutores;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public void setCargaHorariaMaximaPorInstrutor(int cargaHoraria) {
		this.cargaHorariaMaximaPorInstrutor = cargaHoraria;
	}

	public void setMaximoDeTurmasSeguidasPorInstrutor(int numeroDeTurmasSeguidas) {
		this.maximoDeTurmasSeguidasPorInstrutor = numeroDeTurmasSeguidas;
	}

	public static void main(String[] args) {
		JSONHelper jsonHelper = new JSONHelper();

		String jsonInstrutores = jsonHelper.carregaRecursoJSON("instrutores-cweb.json");
		String jsonCursos = jsonHelper.carregaRecursoJSON("cursos-cweb.json");
		String jsonTurmasCWeb = jsonHelper.carregaRecursoJSON("turmas-cweb.json");

		List<Instrutor> instrutores = new InstrutoresHelper().constroiListaAPartirDeJSON(jsonInstrutores);
		List<Curso> cursos = new CursosHelper().constroiListaAPartirDeJSON(jsonCursos);
		List<Turma> turmas = new TurmasCWebHelper(cursos).getTurmas(jsonTurmasCWeb);

		Escalonador escalonador = new Escalonador(cursos, instrutores, turmas, 180, 2);
		AlocacaoDeInstrutores alocacao = escalonador.executa();
		System.out.println(alocacao);
	}

	public AlocacaoDeInstrutores executa() {
		SolverFactory<AlocacaoDeInstrutores> solverFactory = SolverFactory
				.createFromXmlResource("br/com/caelum/escalonadorteste/conf/alocacaoSolverConfig.xml");
		Solver<AlocacaoDeInstrutores> solver = solverFactory.buildSolver();

		AlocacaoDeInstrutores alocacaoNaoResolvida = new AlocacaoDeInstrutores(turmas, instrutores, cursos,
				cargaHorariaMaximaPorInstrutor, maximoDeTurmasSeguidasPorInstrutor);
		AlocacaoDeInstrutores alocacao = solver.solve(alocacaoNaoResolvida);

		System.out.println(solver.explainBestScore());

//		ScoreDirector<AlocacaoDeInstrutores> scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector();
//		scoreDirector.setWorkingSolution(alocacao);
//		for (ConstraintMatchTotal constraintMatchTotal : scoreDirector.getConstraintMatchTotals()) {
//			System.out.println("ID: " + constraintMatchTotal.getConstraintId());
//			System.out.println("Name: " + constraintMatchTotal.getConstraintName());
//			System.out.println("Package: " + constraintMatchTotal.getConstraintPackage());
//			for (ConstraintMatch match : constraintMatchTotal.getConstraintMatchSet()) {
//				System.out.println("\tID: " + match.getConstraintId());
//				System.out.println("\tName: " + match.getConstraintName());
//				System.out.println("\tPackage: " + match.getConstraintPackage());
//				System.out.println("\tId String: " + match.getIdentificationString());
//				for (Object justification : match.getJustificationList()) {
//					System.out.println("\t\tJustification: " + justification);
//				}
//			}
//		}

		return alocacao;
	}
}

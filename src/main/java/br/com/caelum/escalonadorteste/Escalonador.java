package br.com.caelum.escalonadorteste;

import java.util.List;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

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

		return alocacao;
	}
}

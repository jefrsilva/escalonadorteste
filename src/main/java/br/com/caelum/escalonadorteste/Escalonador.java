package br.com.caelum.escalonadorteste;

import java.util.List;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import br.com.caelum.escalonadorteste.alocacao.AlocacaoDeInstrutores;
import br.com.caelum.escalonadorteste.helper.CursosHelper;
import br.com.caelum.escalonadorteste.helper.InstrutoresHelper;
import br.com.caelum.escalonadorteste.helper.TurmasHelper;
import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Turma;

public class Escalonador {

	private List<Turma> turmas;
	private List<Instrutor> instrutores;
	private List<Curso> cursos;
	private int cargaHorariaMaximaPorInstrutor;
	private int maximoDeTurmasSeguidasPorInstrutor;

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
		for (Turma turma : turmas) {
			System.out.println(turma);
		}
	}

	public void setInstrutores(List<Instrutor> instrutores) {
		this.instrutores = instrutores;
		for (Instrutor instrutor : instrutores) {
			System.out.println(instrutor);
		}
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
		for (Curso curso : cursos) {
			System.out.println(curso);
		}
	}

	public void setCargaHorariaMaximaPorInstrutor(int cargaHoraria) {
		this.cargaHorariaMaximaPorInstrutor = cargaHoraria;
	}

	public void setMaximoDeTurmasSeguidasPorInstrutor(int numeroDeTurmasSeguidas) {
		this.maximoDeTurmasSeguidasPorInstrutor = numeroDeTurmasSeguidas;
	}

	public static void main(String[] args) {
		Escalonador escalonador = new Escalonador();

		InstrutoresHelper instrutoresHelper = new InstrutoresHelper();
		String jsonInstrutores = instrutoresHelper.carregaRecursoJSON("instrutores.json");
		List<Instrutor> instrutores = instrutoresHelper.constroiListaAPartirDeJSON(jsonInstrutores);
		escalonador.setInstrutores(instrutores);

		CursosHelper cursosHelper = new CursosHelper();
		String jsonCursos = cursosHelper.carregaRecursoJSON("cursos.json");
		List<Curso> cursos = cursosHelper.constroiListaAPartirDeJSON(jsonCursos);
		escalonador.setCursos(cursos);

		TurmasHelper turmasHelper = new TurmasHelper();
		String jsonTurmas = turmasHelper.carregaRecursoJSON("turmas.json");
		List<Turma> turmas = turmasHelper.constroiListaAPartirDeJSON(jsonTurmas);
		escalonador.setTurmas(turmas);
		
		escalonador.setCargaHorariaMaximaPorInstrutor(160);
		escalonador.setMaximoDeTurmasSeguidasPorInstrutor(2);

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

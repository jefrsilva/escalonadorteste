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

		escalonador.executa();
	}

	public void executa() {
		SolverFactory<AlocacaoDeInstrutores> solverFactory = SolverFactory
				.createFromXmlResource("br/com/caelum/escalonadorteste/conf/alocacaoSolverConfig.xml");
		Solver<AlocacaoDeInstrutores> solver = solverFactory.buildSolver();

		AlocacaoDeInstrutores alocacaoNaoResolvida = new AlocacaoDeInstrutores(turmas, instrutores, cursos, 160, 2);
		AlocacaoDeInstrutores alocacao = solver.solve(alocacaoNaoResolvida);

		for (Turma turma : alocacao.getTurmas()) {
			System.out.println(
					turma.getCodigoCurso() + " (" + turma.getDataDeInicio() + ") -> " + turma.getInstrutor().getNome());
		}
		System.out.println(alocacao.getScore());
	}

}

package br.com.caelum.escalonadorteste.alocacao;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import br.com.caelum.escalonadorteste.conf.ParametrosDeEscalonamento;
import br.com.caelum.escalonadorteste.modelo.Curso;
import br.com.caelum.escalonadorteste.modelo.Instrutor;
import br.com.caelum.escalonadorteste.modelo.Turma;

@PlanningSolution
public class AlocacaoDeInstrutores {

	private HardSoftScore score;
	private List<Instrutor> instrutores = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();

	@ConstraintConfigurationProvider
	private ParametrosDeEscalonamento parametrosDeEscalonamento;

	public AlocacaoDeInstrutores() {
	}

	public AlocacaoDeInstrutores(List<Turma> turmas, List<Instrutor> instrutores, List<Curso> cursos,
			int numeroMaximoDeAulasPorInstrutor, int numeroMaximoDeTurmasSeguidas) {
		this.turmas = turmas;
		this.instrutores = instrutores;
		this.cursos = cursos;
		this.parametrosDeEscalonamento = new ParametrosDeEscalonamento(numeroMaximoDeAulasPorInstrutor,
				numeroMaximoDeTurmasSeguidas);
	}

	@PlanningEntityCollectionProperty
	private List<Turma> turmas = new ArrayList<>();

	public List<Turma> getTurmas() {
		return turmas;
	}

	@ValueRangeProvider(id = "listaDeInstrutores")
	@ProblemFactCollectionProperty
	public List<Instrutor> getInstrutores() {
		return instrutores;
	}

	@ValueRangeProvider(id = "listaDeCursos")
	@ProblemFactCollectionProperty
	public List<Curso> getCursos() {
		return cursos;
	}

	public ParametrosDeEscalonamento getParametrosDeEscalonamento() {
		return parametrosDeEscalonamento;
	}

	@PlanningScore
	public HardSoftScore getScore() {
		return score;
	}

	public void setScore(HardSoftScore score) {
		this.score = score;
	}

	@Override
	public String toString() {
		String saida = "";
		for (Turma turma : turmas) {
			saida += turma.getCodigoCurso() + " " + turma.getPeriodo() + " (" + turma.getDataDeInicio() + ") -> "
					+ turma.getInstrutor().getNome() + "\n";
		}
		saida += getScore() + "\n";
		return saida;
	}
}

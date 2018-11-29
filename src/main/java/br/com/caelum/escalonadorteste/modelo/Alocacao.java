package br.com.caelum.escalonadorteste.modelo;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Alocacao {

	private HardSoftScore score;
	private List<Instrutor> instrutores = new ArrayList<>();
	private Configuracao config;

	public Alocacao() {
	}

	public Alocacao(List<Turma> turmas, List<Instrutor> instrutores, int numeroMaximoDeAulasPorInstrutor,
			int numeroMaximoDeTurmasSeguidas) {
		this.turmas = turmas;
		this.instrutores = instrutores;
		this.config = new Configuracao(numeroMaximoDeAulasPorInstrutor, numeroMaximoDeTurmasSeguidas);
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

	@ProblemFactProperty
	public Configuracao getConfiguracao() {
		return config;
	}

	@PlanningScore
	public HardSoftScore getScore() {
		return score;
	}

	public void setScore(HardSoftScore score) {
		this.score = score;
	}

}

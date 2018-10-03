package br.com.caelum.escalonadorteste.modelo;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Alocacao {
	private HardSoftScore score;
	private List<Instrutor> instrutores = new ArrayList<>();

	public Alocacao() {
	}
	
	public Alocacao(List<Turma> turmas, List<Instrutor> instrutores) {
		this.turmas = turmas;
		this.instrutores = instrutores;
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

	@PlanningScore
	public HardSoftScore getScore() {
		return score;
	}

	public void setScore(HardSoftScore score) {
		this.score = score;
	}
}

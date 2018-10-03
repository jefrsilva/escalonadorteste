package br.com.caelum.escalonadorteste.modelo;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Turma {
	private String codigoDoCurso;
	private Instrutor instrutor;

	public Turma() {
	}
	
	public Turma(String codigoDoCurso) {
		this.codigoDoCurso = codigoDoCurso;
	}

	public String getCodigoDoCurso() {
		return codigoDoCurso;
	}

	@PlanningVariable(valueRangeProviderRefs = "listaDeInstrutores")
	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}

	@Override
	public String toString() {
		return codigoDoCurso + " - " + instrutor.getNome();
	}
}

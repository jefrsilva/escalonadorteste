package br.com.caelum.escalonadorteste.modelo;

import java.time.LocalDate;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Turma {
	private String codigoDoCurso;
	private Instrutor instrutor;
	private LocalDate dataDeInicio;
	private LocalDate dataDeTermino;

	public Turma() {
	}

	public Turma(String codigoDoCurso, LocalDate dataDeInicio, LocalDate dataDeTermino) {
		this.codigoDoCurso = codigoDoCurso;
		this.dataDeInicio = dataDeInicio;
		this.dataDeTermino = dataDeTermino;
	}

	public String getCodigoDoCurso() {
		return codigoDoCurso;
	}

	public LocalDate getDataDeInicio() {
		return dataDeInicio;
	}

	public LocalDate getDataDeTermino() {
		return dataDeTermino;
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

	public boolean conflitaCom(Turma turma) {
		return !(turma.getDataDeInicio().isAfter(this.dataDeTermino)
				|| turma.getDataDeTermino().isBefore(this.dataDeInicio));
	}
}

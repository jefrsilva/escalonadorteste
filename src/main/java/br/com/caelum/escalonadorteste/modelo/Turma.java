package br.com.caelum.escalonadorteste.modelo;

import java.time.LocalDate;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Turma {
	private String codigoDoCurso;
	private Instrutor instrutorFixo;
	private Instrutor instrutor;
	private LocalDate dataDeInicio;
	private LocalDate dataDeTermino;
	private int cargaHoraria;
	private Periodo periodo;

	public Turma() {
	}

	public Turma(String codigoDoCurso, LocalDate dataDeInicio, LocalDate dataDeTermino, int cargaHoraria, Periodo periodo) {
		this.codigoDoCurso = codigoDoCurso;
		this.dataDeInicio = dataDeInicio;
		this.dataDeTermino = dataDeTermino;
		this.cargaHoraria = cargaHoraria;
		this.periodo = periodo;
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

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public Periodo getPeriodo() {
		return periodo;
	}
	
	@PlanningVariable(valueRangeProviderRefs = "listaDeInstrutores")
	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}
	
	public Instrutor getInstrutorFixo() {
		return instrutorFixo;
	}
	
	public void setInstrutorFixo(Instrutor instrutorFixo) {
		this.instrutorFixo = instrutorFixo;
	}
	
	@Override
	public String toString() {
		return codigoDoCurso + " - " + instrutor.getNome();
	}

	public boolean dataConflitaCom(Turma turma) {
		return !(turma.getDataDeInicio().isAfter(this.dataDeTermino)
				|| turma.getDataDeTermino().isBefore(this.dataDeInicio));
	}
}

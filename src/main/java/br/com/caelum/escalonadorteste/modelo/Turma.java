package br.com.caelum.escalonadorteste.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@PlanningEntity
public class Turma {

	private String codigoCurso;
	private Periodo periodo;
	private List<Aula> aulas;

	private Instrutor instrutor;
	private Instrutor instrutorPlanejado;
	private boolean fixo;

	public Turma() {
	}

	@JsonCreator
	public Turma(@JsonProperty("codigoCurso") String codigoCurso, @JsonProperty("periodo") Periodo periodo,
			@JsonProperty("diasDeAula") List<LocalDate> diasDeAula) {
		this.codigoCurso = codigoCurso;
		this.periodo = periodo;
		this.aulas = diasDeAula.stream().map(dia -> new Aula(dia.atTime(this.periodo.getHoraDeInicio()),
				dia.atTime(this.periodo.getHoraDeTermino()))).collect(Collectors.toList());
		this.aulas.sort((umaData, outraData) -> umaData.getInstanteInicial().compareTo(outraData.getInstanteInicial()));
	}

	public String getCodigoCurso() {
		return codigoCurso;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public LocalDate getDataDeInicio() {
		Aula primeiraAula = aulas.get(0);
		return primeiraAula.getDia();
	}

	public LocalDate getDataDeTermino() {
		Aula ultimaAula = aulas.get(aulas.size() - 1);
		return ultimaAula.getDia();
	}

	@PlanningVariable(valueRangeProviderRefs = "listaDeInstrutores")
	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}

	public Instrutor getInstrutorPlanejado() {
		return instrutorPlanejado;
	}

	public void setInstrutorPlanejado(Instrutor instrutorPlanejado) {
		this.instrutorPlanejado = instrutorPlanejado;
	}

	@PlanningPin
	public boolean isFixo() {
		return fixo;
	}

	public void setFixo(boolean fixo) {
		this.fixo = fixo;
	}
	
	@Override
	public String toString() {
		return "Turma [codigoCurso=" + codigoCurso + ", periodo=" + periodo + ", instrutor=" + instrutor
				+ ", getDataDeInicio()=" + getDataDeInicio() + ", getDataDeTermino()=" + getDataDeTermino() + "]";
	}

	public boolean aulasConflitamCom(Turma turma) {
		int indiceMinhasAulas = 0;
		int indiceOutrasAulas = 0;
		boolean conflitou = false;
		while (!conflitou) {
			Aula minhaAula = this.aulas.get(indiceMinhasAulas);
			Aula outraAula = turma.getAulas().get(indiceOutrasAulas);

			LocalDate diaDaMinhaAula = minhaAula.getDia();
			LocalDate diaDaOutraAula = outraAula.getDia();

			if (diaDaMinhaAula.equals(diaDaOutraAula)) {
				if (minhaAula.getHorarioDeInicio().isAfter(outraAula.getHorarioDeTermino())) {
					indiceOutrasAulas++;
				} else if (outraAula.getHorarioDeInicio().isAfter(minhaAula.getHorarioDeTermino())) {
					indiceMinhasAulas++;
				} else {
					conflitou = true;
				}
			} else if (diaDaMinhaAula.isBefore(diaDaOutraAula)) {
				indiceMinhasAulas++;
			} else if (diaDaOutraAula.isBefore(diaDaMinhaAula)) {
				indiceOutrasAulas++;
			}

			if (indiceMinhasAulas >= aulas.size() || indiceOutrasAulas >= turma.getAulas().size()) {
				break;
			}
		}
		return conflitou;
	}
}

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
	private boolean ehViagem;

	private Instrutor instrutor;
	private Instrutor instrutorPlanejado;
	private boolean fixo;

	public Turma() {
	}

	@JsonCreator
	public Turma(@JsonProperty("codigoCurso") String codigoCurso, @JsonProperty("periodo") Periodo periodo,
			@JsonProperty("diasDeAula") List<LocalDate> diasDeAula, @JsonProperty("ehViagem") boolean ehViagem) {
		this.codigoCurso = codigoCurso;
		this.periodo = periodo;
		this.ehViagem = ehViagem;
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

	public boolean ehViagem() {
		return ehViagem;
	}

	public LocalDate getDataDeInicio() {
		Aula primeiraAula = aulas.get(0);
		return primeiraAula.getDiaDeInicio();
	}

	public LocalDate getDataDeTermino() {
		Aula ultimaAula = aulas.get(aulas.size() - 1);
		return ultimaAula.getDiaDeInicio();
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

			LocalDate diaDaMinhaAula = minhaAula.getDiaDeInicio();
			LocalDate diaDaOutraAula = outraAula.getDiaDeInicio();

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

	public boolean aulasConflitamComViagens(Turma turmaComViagem) {
		if (turmaComViagem.ehViagem()) {
			LocalDate diaDaViagemDeIda = turmaComViagem.getDataDeInicio().minusDays(1);
			LocalDate diaDaViagemDeVolta = turmaComViagem.getDataDeTermino().plusDays(2);

			Intervalo intervaloDaViagem = new Intervalo(diaDaViagemDeIda.atStartOfDay(),
					diaDaViagemDeVolta.atStartOfDay());
			for (Aula aula : aulas) {
				if (intervaloDaViagem.contem(aula.getInstanteInicial())
						|| intervaloDaViagem.contem(aula.getInstanteInicial())) {
					return true;
				}
			}
		}

		return false;
	}
}

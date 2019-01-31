package br.com.caelum.escalonadorteste.cweb;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SemanaCWebDTO {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data;
	private List<TurmaCWebDTO> turmasDaSemana;

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public List<TurmaCWebDTO> getTurmasDaSemana() {
		return turmasDaSemana;
	}

	public void setTurmasDaSemana(List<TurmaCWebDTO> turmas) {
		this.turmasDaSemana = turmas;
	}

}

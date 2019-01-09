package br.com.caelum.escalonadorteste.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Aula {

	private LocalDateTime instanteInicial;
	private LocalDateTime instanteFinal;

	public Aula(LocalDateTime instanteInicial, LocalDateTime instanteFinal) {
		this.instanteInicial = instanteInicial;
		this.instanteFinal = instanteFinal;
	}

	public LocalDateTime getInstanteInicial() {
		return instanteInicial;
	}

	public LocalDateTime getInstanteFinal() {
		return instanteFinal;
	}

	public LocalDate getDia() {
		return instanteInicial.toLocalDate();
	}

	public LocalTime getHorarioDeInicio() {
		return instanteInicial.toLocalTime();
	}

	public LocalTime getHorarioDeTermino() {
		return instanteFinal.toLocalTime();
	}

}

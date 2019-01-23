package br.com.caelum.escalonadorteste.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Aula {

	private Intervalo intervalo;

	public Aula(LocalDateTime instanteInicial, LocalDateTime instanteFinal) {
		this.intervalo = new Intervalo(instanteInicial, instanteFinal);
	}

	public LocalDateTime getInstanteInicial() {
		return intervalo.getInstanteInicial();
	}

	public LocalDateTime getInstanteFinal() {
		return intervalo.getInstanteFinal();
	}

	public LocalDate getDiaDeInicio() {
		return intervalo.getInstanteInicial().toLocalDate();
	}

	public LocalTime getHorarioDeInicio() {
		return intervalo.getInstanteInicial().toLocalTime();
	}

	public LocalTime getHorarioDeTermino() {
		return intervalo.getInstanteFinal().toLocalTime();
	}

}

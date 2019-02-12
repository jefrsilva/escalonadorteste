package br.com.caelum.escalonadorteste.modelo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Intervalo {

	private LocalDateTime instanteInicial;
	private LocalDateTime instanteFinal;

	@JsonCreator
	public Intervalo(@JsonProperty("inicio") LocalDateTime instanteInicial,
			@JsonProperty("termino") LocalDateTime instanteFinal) {
		this.instanteInicial = instanteInicial;
		this.instanteFinal = instanteFinal;
	}

	public LocalDateTime getInstanteInicial() {
		return instanteInicial;
	}

	public LocalDateTime getInstanteFinal() {
		return instanteFinal;
	}

	public boolean contem(LocalDateTime instante) {
		return !(instante.isBefore(instanteInicial) || instante.isAfter(instanteFinal));
	}
}
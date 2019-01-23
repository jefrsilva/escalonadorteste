package br.com.caelum.escalonadorteste.modelo;

import java.time.LocalDateTime;

public class Intervalo {

	private LocalDateTime instanteInicial;
	private LocalDateTime instanteFinal;

	public Intervalo(LocalDateTime instanteInicial, LocalDateTime instanteFinal) {
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
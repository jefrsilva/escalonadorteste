package br.com.caelum.escalonadorteste.modelo;

public class ParametrosDeEscalonamento {

	private int numeroMaximoDeHorasPorInstrutor;
	private int numeroMaximoDeTurmasSeguidas;

	public ParametrosDeEscalonamento(int numeroMaximoDeHorasPorInstrutor, int numeroMaximoDeTurmasSeguidas) {
		this.numeroMaximoDeHorasPorInstrutor = numeroMaximoDeHorasPorInstrutor;
		this.numeroMaximoDeTurmasSeguidas = numeroMaximoDeTurmasSeguidas;
	}

	public int getNumeroMaximoDeHorasPorInstrutor() {
		return numeroMaximoDeHorasPorInstrutor;
	}

	public int getNumeroMaximoDeTurmasSeguidas() {
		return numeroMaximoDeTurmasSeguidas;
	}
}

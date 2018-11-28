package br.com.caelum.escalonadorteste.modelo;

public class Configuracao {

	private int numeroMaximoDeAulasPorInstrutor;
	private int numeroMaximoDeTurmasSeguidas;

	public Configuracao(int numeroMaximoDeAulasPorInstrutor, int numeroMaximoDeTurmasSeguidas) {
		this.numeroMaximoDeAulasPorInstrutor = numeroMaximoDeAulasPorInstrutor;
		this.numeroMaximoDeTurmasSeguidas = numeroMaximoDeTurmasSeguidas;
	}

	public int getNumeroMaximoDeAulasPorInstrutor() {
		return numeroMaximoDeAulasPorInstrutor;
	}

	public int getNumeroMaximoDeTurmasSeguidas() {
		return numeroMaximoDeTurmasSeguidas;
	}
}

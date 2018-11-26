package br.com.caelum.escalonadorteste.modelo;

public class Configuracao {
	
	private int numeroMaximoDeAulasPorInstrutor;
	
	public Configuracao(int numeroMaximoDeAulasPorInstrutor) {
		this.numeroMaximoDeAulasPorInstrutor = numeroMaximoDeAulasPorInstrutor;
	}
	
	public int getNumeroMaximoDeAulasPorInstrutor() {
		return numeroMaximoDeAulasPorInstrutor;
	}
}

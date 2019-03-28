package br.com.caelum.escalonadorteste.modelo;

public class TotalDeHoras {

	private Instrutor instrutor;
	private int horas;

	public TotalDeHoras(Instrutor instrutor, int horas) {
		super();
		this.instrutor = instrutor;
		this.horas = horas;
	}

	public Instrutor getInstrutor() {
		return instrutor;
	}

	public int getHoras() {
		return horas;
	}
	
}

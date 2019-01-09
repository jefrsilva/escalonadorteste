package br.com.caelum.escalonadorteste.modelo;

public class Curso {

	private String codigo;
	private int cargaHoraria;

	public Curso(String codigo, int cargaHoraria) {
		this.codigo = codigo;
		this.cargaHoraria = cargaHoraria;
	}

	public String getCodigo() {
		return codigo;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}
	
}

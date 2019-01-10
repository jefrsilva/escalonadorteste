package br.com.caelum.escalonadorteste.modelo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Curso {

	private String codigo;
	private int cargaHoraria;

	@JsonCreator
	public Curso(@JsonProperty("codigo") String codigo, @JsonProperty("cargaHoraria") int cargaHoraria) {
		this.codigo = codigo;
		this.cargaHoraria = cargaHoraria;
	}

	public String getCodigo() {
		return codigo;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	@Override
	public String toString() {
		return "Curso [codigo=" + codigo + ", cargaHoraria=" + cargaHoraria + "]";
	}
	
}

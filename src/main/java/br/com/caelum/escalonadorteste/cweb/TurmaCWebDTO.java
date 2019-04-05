package br.com.caelum.escalonadorteste.cweb;

import java.util.List;

public class TurmaCWebDTO {

	private Integer id;

	private String codigo;

	private String periodo;

	private String sala;

	private boolean viagem;

	private List<String> dias;

	private String instrutor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public boolean isViagem() {
		return viagem;
	}

	public void setViagem(boolean viagem) {
		this.viagem = viagem;
	}

	public List<String> getDias() {
		return dias;
	}

	public void setDias(List<String> dias) {
		this.dias = dias;
	}

	public String getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(String instrutor) {
		this.instrutor = instrutor;
	}

}

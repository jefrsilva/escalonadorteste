package br.com.caelum.escalonadorteste.cweb;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TurmaCWebDTO {

	private Integer id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate primeiroDia;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate ultimoDia;
	private String horario;
	private String nomesInstrutores;
	private String codigoCurso;
	private String cidade;
	private boolean incompany;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getPrimeiroDia() {
		return primeiroDia;
	}

	public void setPrimeiroDia(LocalDate primeiroDia) {
		this.primeiroDia = primeiroDia;
	}

	public LocalDate getUltimoDia() {
		return ultimoDia;
	}

	public void setUltimoDia(LocalDate ultimoDia) {
		this.ultimoDia = ultimoDia;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getNomesInstrutores() {
		return nomesInstrutores;
	}

	public void setNomesInstrutores(String nomesInstrutores) {
		this.nomesInstrutores = nomesInstrutores;
	}

	public String getCodigoCurso() {
		return codigoCurso;
	}

	public void setCodigoCurso(String codigoCurso) {
		this.codigoCurso = codigoCurso;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public boolean isIncompany() {
		return incompany;
	}

	public void setIncompany(boolean incompany) {
		this.incompany = incompany;
	}

}

package br.com.caelum.escalonadorteste.modelo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Instrutor {
	private String nome;

	@JsonProperty("cursos")
	private List<String> cursosConhecidos;

	@JsonProperty("periodos")
	private List<Periodo> periodosViaveis;
	
	@JsonProperty("podeViajar")
	private boolean disponivelParaViagem;

	@JsonCreator
	public Instrutor(@JsonProperty("nome") String nome, @JsonProperty("cursos") List<String> cursosConhecidos,
			@JsonProperty("periodos") List<Periodo> periodosViaveis, @JsonProperty("podeViajar") boolean disponivelParaViagem) {
		this.nome = nome;
		this.cursosConhecidos = cursosConhecidos;
		this.periodosViaveis = periodosViaveis;
		this.disponivelParaViagem = disponivelParaViagem;
	}

	public String getNome() {
		return nome;
	}

	public boolean sabeDarOCurso(Turma turma) {
		return cursosConhecidos.contains(turma.getCodigoCurso());
	}

	public boolean consegueDarAulaNoPeriodo(Turma turma) {
		return periodosViaveis.contains(turma.getPeriodo());
	}
	
	public boolean estaDisponivelParaViagem() {
		return disponivelParaViagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instrutor other = (Instrutor) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Instrutor [nome=" + nome + ", cursosConhecidos=" + cursosConhecidos + ", periodosViaveis="
				+ periodosViaveis + "]";
	}
}

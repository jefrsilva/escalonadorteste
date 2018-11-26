package br.com.caelum.escalonadorteste.modelo;

import java.util.List;

public class Instrutor {
	private String nome;
	private List<String> turmasViaveis;

	public Instrutor(String nome, List<String> turmasViaveis) {
		this.nome = nome;
		this.turmasViaveis = turmasViaveis;
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

	public String getNome() {
		return nome;
	}

	public boolean consegueMinistrar(Turma turma) {
		return turmasViaveis.contains(turma.getCodigoDoCurso());
	}
}

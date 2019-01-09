package br.com.caelum.escalonadorteste.modelo;

import java.util.List;

public class Instrutor {
	private String nome;
	private List<String> cursosConhecidos;
	private List<Periodo> periodosViaveis;

	public Instrutor(String nome, List<String> cursosConhecidos, List<Periodo> periodosViaveis) {
		this.nome = nome;
		this.cursosConhecidos = cursosConhecidos;
		this.periodosViaveis = periodosViaveis;
	}

	public String getNome() {
		return nome;
	}

	public boolean sabeDarOCurso(Turma turma) {
		return cursosConhecidos.contains(turma.getCodigoDoCurso());
	}

	public boolean consegueDarAulaNoPeriodo(Turma turma) {
		return periodosViaveis.contains(turma.getPeriodo());
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
}

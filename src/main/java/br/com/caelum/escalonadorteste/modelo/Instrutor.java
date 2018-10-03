package br.com.caelum.escalonadorteste.modelo;

import java.util.List;

public class Instrutor {
	private String nome;
	private List<String> turmasViaveis;

	public Instrutor(String nome, List<String> turmasViaveis) {
		this.nome = nome;
		this.turmasViaveis = turmasViaveis;
	}

	public String getNome() {
		return nome;
	}

	public boolean consegueMinistrar(Turma turma) {
		return turmasViaveis.contains(turma.getCodigoDoCurso());
	}
}

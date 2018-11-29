package br.com.caelum.escalonadorteste.funcao;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import br.com.caelum.escalonadorteste.modelo.Turma;

public class ContextoDasTurmas implements Serializable {

	private static final long serialVersionUID = -548055600573031230L;

	private List<Turma> turmas = new LinkedList<>();

	public List<Turma> getTurmas() {
		return turmas;
	}
}

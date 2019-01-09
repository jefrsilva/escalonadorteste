package br.com.caelum.escalonadorteste.funcao;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.kie.api.runtime.rule.AccumulateFunction;

import br.com.caelum.escalonadorteste.modelo.Turma;

public class AcumuladorDeMaximoDeTurmasSeguidas implements AccumulateFunction<ContextoDasTurmas> {

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
	}

	@Override
	public void accumulate(ContextoDasTurmas contexto, Object obj) {
		Turma turma = (Turma) obj;
		contexto.getTurmas().add(turma);
	}

	@Override
	public ContextoDasTurmas createContext() {
		return new ContextoDasTurmas();
	}

	@Override
	public Object getResult(ContextoDasTurmas contexto) throws Exception {
		int turmasSeguidas = 0;
		int numeroMaximoDeTurmasSeguidas = 0;

		List<Turma> turmas = new LinkedList<>();
		turmas.addAll(contexto.getTurmas());

		Collections.sort(turmas, (a, b) -> a.getDataDeInicio().compareTo(b.getDataDeInicio()));

		LocalDate dataDeInicioDaUltimaTurma = LocalDate.of(1990, 1, 1);
		LocalDate dataDeTerminoDaUltimaTurma = LocalDate.of(1990, 1, 1);
		for (Turma turma : turmas) {
			if (dataEstaNoIntervalo(turma.getDataDeInicio().minusWeeks(1), dataDeInicioDaUltimaTurma,
					dataDeTerminoDaUltimaTurma)) {
				turmasSeguidas++;
			} else {
				turmasSeguidas = 1;
			}
			numeroMaximoDeTurmasSeguidas = Math.max(numeroMaximoDeTurmasSeguidas, turmasSeguidas);
			dataDeInicioDaUltimaTurma = turma.getDataDeInicio();
			dataDeTerminoDaUltimaTurma = turma.getDataDeTermino();
		}

		return numeroMaximoDeTurmasSeguidas;
	}

	private boolean dataEstaNoIntervalo(LocalDate data, LocalDate inicio, LocalDate fim) {
		return (data.equals(inicio) || data.equals(fim) || (data.isAfter(inicio) && data.isBefore(fim)));
	}

	@Override
	public Class<?> getResultType() {
		return Integer.class;
	}

	@Override
	public void init(ContextoDasTurmas contexto) throws Exception {
		contexto.getTurmas().clear();
	}

	@Override
	public void reverse(ContextoDasTurmas contexto, Object obj) throws Exception {
		Turma turma = (Turma) obj;
		contexto.getTurmas().remove(turma);
	}

	@Override
	public boolean supportsReverse() {
		return true;
	}

}

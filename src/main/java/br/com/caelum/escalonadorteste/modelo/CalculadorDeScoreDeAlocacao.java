package br.com.caelum.escalonadorteste.modelo;

import java.util.HashMap;
import java.util.Map;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class CalculadorDeScoreDeAlocacao implements EasyScoreCalculator<Alocacao> {
	private int hardScore;
	private int softScore;

	public HardSoftScore calculateScore(Alocacao alocacao) {
		hardScore = 0;
		softScore = 0;

		Map<String, Integer> turmasMinistradas = new HashMap<>();

		for (Instrutor instrutor : alocacao.getInstrutores()) {
			turmasMinistradas.put(instrutor.getNome(), 0);
		}

		for (Turma turma : alocacao.getTurmas()) {
			if (turma.getInstrutor() != null) {
				if (!turma.getInstrutor().consegueMinistrar(turma)) {
					hardScore -= 10;
				}
				turmasMinistradas.put(turma.getInstrutor().getNome(),
						turmasMinistradas.get(turma.getInstrutor().getNome()) + 1);
			}
		}

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		for (int value : turmasMinistradas.values()) {
			min = Math.min(min, value);
			max = Math.max(max, value);
		}

		softScore = -10 * (max - min);

		return HardSoftScore.valueOf(hardScore, softScore);
	}
}

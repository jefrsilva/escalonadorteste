package br.com.caelum.escalonadorteste.conf;

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@ConstraintConfiguration(constraintPackage = "escalonador.parametros")
public class ParametrosDeEscalonamento {
	
	@ConstraintWeight("não sabe dar o curso")
	private HardSoftScore naoSabeDarOCurso = HardSoftScore.ofHard(10);
	
	@ConstraintWeight("não consegue dar aula no período")
	private HardSoftScore naoConsegueDarAulaNoPeriodo = HardSoftScore.ofHard(10);
	
	@ConstraintWeight("pegou aula em período fora dos preferenciais")
	private HardSoftScore naoPrefereDarAulaNoPeriodo = HardSoftScore.ofSoft(20);
	
	@ConstraintWeight("não está disponível nos dias da turma")
	private HardSoftScore naoEstaDisponivelNosDiasDaTurma = HardSoftScore.ofHard(10);
	
	@ConstraintWeight("pegou integral e noturno simultaneamente")
	private HardSoftScore pegouIntegralENoturnoSimultaneamente = HardSoftScore.ofHard(10);
	
	@ConstraintWeight("mais de X horas de aula")
	private HardSoftScore maisDeXHorasDeAula = HardSoftScore.ofSoft(1);
	
	@ConstraintWeight("data de um curso conflita com outro")
	private HardSoftScore dataDeUmCursoConflitaComOutro = HardSoftScore.ofHard(10);
	
	@ConstraintWeight("mais de X turmas seguidas")
	private HardSoftScore maisDeXTurmasSeguidas = HardSoftScore.ofSoft(10);
	
	@ConstraintWeight("instrutor planejado foi trocado")
	private HardSoftScore instrutorPlanejadoFoiTrocado = HardSoftScore.ofSoft(1);
	
	@ConstraintWeight("instrutor não está disponível para viagem")
	private HardSoftScore instrutorNaoEstaDisponivelParaViagem = HardSoftScore.ofHard(10);
	
	@ConstraintWeight("aula de sábado durante ou logo após viagem")
	private HardSoftScore aulaDeSabadoDuranteOuAposViagem = HardSoftScore.ofHard(10);
	
	@ConstraintWeight("instrutor está restrito para a turma")
	private HardSoftScore instrutorEstaRestritoParaATurma = HardSoftScore.ofHard(10);
	
	@ConstraintWeight("horas de aulas não foram distribuídas igualmente")
	private HardSoftScore horasDeAulaNaoForamDistribuidasIgualmente = HardSoftScore.ofSoft(1);

	private int numeroMaximoDeHorasPorInstrutor;
	private int numeroMaximoDeTurmasSeguidas;

	public ParametrosDeEscalonamento(int numeroMaximoDeHorasPorInstrutor, int numeroMaximoDeTurmasSeguidas) {
		this.numeroMaximoDeHorasPorInstrutor = numeroMaximoDeHorasPorInstrutor;
		this.numeroMaximoDeTurmasSeguidas = numeroMaximoDeTurmasSeguidas;
	}

	public int getNumeroMaximoDeHorasPorInstrutor() {
		return numeroMaximoDeHorasPorInstrutor;
	}

	public int getNumeroMaximoDeTurmasSeguidas() {
		return numeroMaximoDeTurmasSeguidas;
	}
}

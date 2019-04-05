package br.com.caelum.escalonadorteste.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Instrutor {
	@JsonProperty("nome")
	private String nome;

	@JsonProperty("cursos")
	private List<String> cursosConhecidos;

	@JsonProperty("periodos")
	private List<Periodo> periodosViaveis;
	
	@JsonProperty("periodosPreferenciais")
	private List<Periodo> periodosPreferenciais = new ArrayList<>();

	@JsonProperty("podeViajar")
	private boolean disponivelParaViagem;

	@JsonProperty("indisponibilidades")
	private List<Intervalo> datasIndisponiveis = new ArrayList<>();

	@JsonProperty("externo")
	private boolean externo;

	@JsonCreator
	public Instrutor(@JsonProperty("nome") String nome, @JsonProperty("cursos") List<String> cursosConhecidos,
			@JsonProperty("periodos") List<Periodo> periodosViaveis,
			@JsonProperty("periodosPreferenciais") List<Periodo> periodosPreferenciais,
			@JsonProperty("podeViajar") boolean disponivelParaViagem,
			@JsonProperty("indisponibilidades") List<Intervalo> datasIndisponiveis,
			@JsonProperty("externo") boolean externo) {
		this.nome = nome;
		this.cursosConhecidos = cursosConhecidos;
		this.periodosViaveis = periodosViaveis;
		if (periodosPreferenciais == null) {
			this.periodosPreferenciais.addAll(periodosViaveis);
		} else {
			this.periodosPreferenciais = periodosPreferenciais;	
		}
		this.disponivelParaViagem = disponivelParaViagem;
		if (datasIndisponiveis != null) {
			this.datasIndisponiveis = datasIndisponiveis;
		}
		this.externo = externo;
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
	
	public boolean prefereDarAulaNoPeriodo(Turma turma) {
		return periodosPreferenciais.contains(turma.getPeriodo());
	}

	public boolean estaDisponivelParaViagem() {
		return disponivelParaViagem;
	}

	public boolean estaDisponivelNosDiasDaTurma(Turma turma) {
		boolean estaDisponivel = true;
		for (Intervalo intervalo : datasIndisponiveis) {
			for (Aula aula : turma.getAulas()) {
				if (intervalo.contem(aula.getInstanteInicial())) {
					estaDisponivel = false;
					break;
				}
			}
		}
		return estaDisponivel;
	}

	public boolean isExterno() {
		return externo;
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
				+ periodosViaveis + ", periodosPreferenciais=" + periodosPreferenciais + ", disponivelParaViagem="
				+ disponivelParaViagem + ", datasIndisponiveis=" + datasIndisponiveis + ", externo=" + externo + "]";
	}
	
}

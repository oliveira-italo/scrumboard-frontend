package br.fatec.esiv.srcumboard.form;

import br.fatec.esiv.srcumboard.enuns.StatusTarefa;

public class TarefaForm extends EntityForm {

	private String nome;
	private String descricao;
	private Integer duracaoPrevistaEmHoras;
	private Long sprintId;
	private Long usuarioId;
	private StatusTarefa status;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDuracaoPrevistaEmHoras() {
		return duracaoPrevistaEmHoras;
	}

	public void setDuracaoPrevistaEmHoras(Integer duracaoPrevistaEmHoras) {
		this.duracaoPrevistaEmHoras = duracaoPrevistaEmHoras;
	}

	public Long getSprintId() {
		return sprintId;
	}

	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public StatusTarefa getStatus() {
		return status;
	}

	public void setStatus(StatusTarefa status) {
		this.status = status;
	}

}

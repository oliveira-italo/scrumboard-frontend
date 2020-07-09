package br.fatec.esiv.srcumboard.dto;

import br.fatec.esiv.srcumboard.enuns.StatusTarefa;

public class TarefaDTO {

	private Long id;
	private String nome;
	private String descricao;
	private Integer duracaoPrevistaEmHoras;
	private StatusTarefa status;
	private SprintDTO sprint;
	private UsuarioDTO usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public StatusTarefa getStatus() {
		return status;
	}

	public void setStatus(StatusTarefa status) {
		this.status = status;
	}

	public SprintDTO getSprint() {
		return sprint;
	}

	public void setSprint(SprintDTO sprint) {
		this.sprint = sprint;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

}

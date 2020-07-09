package br.fatec.esiv.srcumboard.form;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.fatec.esiv.srcumboard.enuns.StatusProjeto;

public class ProjetoForm extends EntityForm {

	private String nome;
	private String descricao;
	private LocalDate inicioPrevisto;
	private LocalDate fimPrevisto;
	private LocalDate inicioEfetivo;
	private LocalDate fimEfetivo;
	private Long gerenteId;
	private StatusProjeto status;
	private List<Long> usuariosAlocadosIds = new ArrayList<>();

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

	public LocalDate getInicioPrevisto() {
		return inicioPrevisto;
	}

	public void setInicioPrevisto(LocalDate inicioPrevisto) {
		this.inicioPrevisto = inicioPrevisto;
	}

	public LocalDate getFimPrevisto() {
		return fimPrevisto;
	}

	public void setFimPrevisto(LocalDate fimPrevisto) {
		this.fimPrevisto = fimPrevisto;
	}

	public LocalDate getInicioEfetivo() {
		return inicioEfetivo;
	}

	public void setInicioEfetivo(LocalDate inicioEfetivo) {
		this.inicioEfetivo = inicioEfetivo;
	}

	public LocalDate getFimEfetivo() {
		return fimEfetivo;
	}

	public void setFimEfetivo(LocalDate fimEfetivo) {
		this.fimEfetivo = fimEfetivo;
	}

	public Long getGerenteId() {
		return gerenteId;
	}

	public void setGerenteId(Long gerenteId) {
		this.gerenteId = gerenteId;
	}

	public StatusProjeto getStatus() {
		return status;
	}

	public void setStatus(StatusProjeto status) {
		this.status = status;
	}

	public List<Long> getUsuariosAlocadosIds() {
		return usuariosAlocadosIds;
	}

	public void setUsuariosAlocadosIds(List<Long> usuariosAlocadosIds) {
		this.usuariosAlocadosIds = usuariosAlocadosIds;
	}

}

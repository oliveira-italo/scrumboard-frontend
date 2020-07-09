package br.fatec.esiv.srcumboard.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.fatec.esiv.srcumboard.enuns.StatusProjeto;
import br.fatec.esiv.srcumboard.form.ProjetoForm;

public class ProjetoDTO {

	private Long id;
	private String nome;
	private String descricao;
	private LocalDate inicioPrevisto;
	private LocalDate fimPrevisto;
	private LocalDate inicioEfetivo;
	private LocalDate fimEfetivo;
	private UsuarioDTO gerente;
	private StatusProjeto status;
	private List<UsuarioDTO> usuariosAlocados = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public UsuarioDTO getGerente() {
		return gerente;
	}

	public void setGerente(UsuarioDTO gerente) {
		this.gerente = gerente;
	}

	public StatusProjeto getStatus() {
		return status;
	}

	public void setStatus(StatusProjeto status) {
		this.status = status;
	}

	public List<UsuarioDTO> getUsuariosAlocados() {
		return usuariosAlocados;
	}

	public void setUsuariosAlocados(List<UsuarioDTO> usuariosAlocados) {
		this.usuariosAlocados = usuariosAlocados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProjetoDTO other = (ProjetoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ProjetoForm toForm() {
		ProjetoForm form = new ProjetoForm();
		form.setNome(nome);
		form.setDescricao(descricao);
		form.setInicioPrevisto(inicioPrevisto);
		form.setInicioEfetivo(inicioEfetivo);
		form.setFimPrevisto(fimPrevisto);
		form.setFimEfetivo(fimEfetivo);
		form.setGerenteId(gerente.getId());
		form.setStatus(status);
		form.setUsuariosAlocadosIds(
				usuariosAlocados.stream().map(usuario -> usuario.getId()).collect(Collectors.toList()));

		return form;
	}

}

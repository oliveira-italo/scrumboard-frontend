package br.fatec.esiv.srcumboard.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.fatec.esiv.srcumboard.cache.Cache;
import br.fatec.esiv.srcumboard.client.Clients;
import br.fatec.esiv.srcumboard.dto.ProjetoDTO;
import br.fatec.esiv.srcumboard.dto.UsuarioDTO;
import br.fatec.esiv.srcumboard.enuns.StatusProjeto;
import br.fatec.esiv.srcumboard.util.Redirector;

@SuppressWarnings("serial")
@ManagedBean(name = "usuario")
@ViewScoped
public class UsuarioMB extends BaseMB implements Serializable {

	private UsuarioDTO usuario = new UsuarioDTO();
	private List<ProjetoDTO> projetosAtivosAlocacao = new ArrayList<>();
	private List<ProjetoDTO> projetosInativosAlocacao = new ArrayList<>();
	private List<ProjetoDTO> projetosAtivosGerencia = new ArrayList<>();
	private List<ProjetoDTO> projetosInativosGerencia = new ArrayList<>();

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public List<ProjetoDTO> getProjetosAtivosAlocacao() {
		return projetosAtivosAlocacao;
	}

	public void setProjetosAtivosAlocacao(List<ProjetoDTO> projetosAtivosAlocacao) {
		this.projetosAtivosAlocacao = projetosAtivosAlocacao;
	}

	public List<ProjetoDTO> getProjetosInativosAlocacao() {
		return projetosInativosAlocacao;
	}

	public void setProjetosInativosAlocacao(List<ProjetoDTO> projetosInativosAlocacao) {
		this.projetosInativosAlocacao = projetosInativosAlocacao;
	}

	public List<ProjetoDTO> getProjetosAtivosGerencia() {
		return projetosAtivosGerencia;
	}

	public void setProjetosAtivosGerencia(List<ProjetoDTO> projetosAtivosGerencia) {
		this.projetosAtivosGerencia = projetosAtivosGerencia;
	}

	public List<ProjetoDTO> getProjetosInativosGerencia() {
		return projetosInativosGerencia;
	}

	public void setProjetosInativosGerencia(List<ProjetoDTO> projetosInativosGerencia) {
		this.projetosInativosGerencia = projetosInativosGerencia;
	}

	@PostConstruct
	public void init() {
		carregaUsuario();
		carregaProjetosAlocacao();
		carregaProjetosGerencia();
	}

	private void carregaProjetosGerencia() {
		try {

			List<ProjetoDTO> projetos = Clients.carregarGerenciaUsuario(usuario.getId()).doRequest().get()
					.getProjetos();

			this.projetosAtivosGerencia = projetos.stream()
					.filter(projeto -> projeto.getStatus() == StatusProjeto.ATIVO).collect(Collectors.toList());

			this.projetosInativosGerencia = projetos.stream()
					.filter(projeto -> projeto.getStatus() == StatusProjeto.INATIVO).collect(Collectors.toList());

		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void carregaProjetosAlocacao() {

		try {

			List<ProjetoDTO> projetos = Clients.carregarAlocacaoUsuario(usuario.getId()).doRequest().get()
					.getProjetos();

			this.projetosAtivosAlocacao = projetos.stream()
					.filter(projeto -> projeto.getStatus() == StatusProjeto.ATIVO).collect(Collectors.toList());

			this.projetosInativosAlocacao = projetos.stream()
					.filter(projeto -> projeto.getStatus() == StatusProjeto.INATIVO).collect(Collectors.toList());

		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void carregaUsuario() {
		try {
			this.usuario = Clients.carregarUsuario(Long.parseLong(getUrlParameter("id").get())).doRequest().get();
		} catch (NumberFormatException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void atualizarUsuario() {

		try {
			this.usuario = Clients.atualizarUsuario(usuario.toForm(), usuario.getId()).doRequest().get();
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void novoProjeto() {
		Redirector.to("projeto").withParam("idGerente", usuario.getId()).redirect();
	}

	public void editarProjeto(Long idProjeto) {
		Redirector.to("projeto").withParam("id", idProjeto).redirect();
	}

	public void abrirKanban(Long idProjeto) {
		Redirector.to("kanban").withParam("idProjeto", idProjeto).withParam("idUsuario", usuario.getId()).redirect();
	}

	public void logout() {
		Cache.idUsuarioLogado = null;
		Redirector.to("login").redirect();
	}

}

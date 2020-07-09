package br.fatec.esiv.srcumboard.beans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.fatec.esiv.srcumboard.client.Clients;
import br.fatec.esiv.srcumboard.dto.ProjetoDTO;
import br.fatec.esiv.srcumboard.dto.UsuarioDTO;
import br.fatec.esiv.srcumboard.enuns.StatusProjeto;
import br.fatec.esiv.srcumboard.util.Messenger;
import br.fatec.esiv.srcumboard.util.Redirector;

@ManagedBean(name = "projetomb")
@ViewScoped
public class ProjetoMB extends BaseMB {

	private ProjetoDTO projeto = new ProjetoDTO();
	private boolean novo;
	private StatusProjeto[] statusValues = StatusProjeto.values();

	private String paramPesquisaUsuario = "";
	private List<UsuarioDTO> resultPesquisaUsuarioAlocacao = new ArrayList<>();

	public List<UsuarioDTO> getResultPesquisaUsuarioAlocacao() {
		return resultPesquisaUsuarioAlocacao;
	}

	public void setResultPesquisaUsuarioAlocacao(List<UsuarioDTO> resultPesquisaUsuarioAlocacao) {
		this.resultPesquisaUsuarioAlocacao = resultPesquisaUsuarioAlocacao;
	}

	public void setParamPesquisaUsuario(String paramPesquisaUsuario) {
		this.paramPesquisaUsuario = paramPesquisaUsuario;
	}

	public String getParamPesquisaUsuario() {
		return paramPesquisaUsuario;
	}

	public StatusProjeto[] getStatusValues() {
		return statusValues;
	}

	public ProjetoDTO getProjeto() {
		return projeto;
	}

	public void setProjeto(ProjetoDTO projeto) {
		this.projeto = projeto;
	}

	@PostConstruct
	public void init() {
		try {

			this.novo = true;
			Optional<String> idProjeto = getUrlParameter("id");
			if (idProjeto.isPresent()) {

				this.projeto = Clients.carregarProjeto(Long.parseLong(idProjeto.get())).doRequest().get();
				this.novo = false;

			} else {

				Long idGerente = Long.parseLong(getUrlParameter("idGerente").get());
				UsuarioDTO gerente = Clients.carregarUsuario(idGerente).doRequest().get();
				projeto.setGerente(gerente);
			}

		} catch (NumberFormatException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void salvar() {

		if (isValido()) {
			try {

				if (isNovo()) {
					projeto.setStatus(StatusProjeto.ATIVO);
					this.projeto = Clients.cadastrarProjeto(projeto.toForm()).doRequest().get();
				} else {
					this.projeto = Clients.atualizarProjeto(projeto.toForm(), projeto.getId()).doRequest().get();
				}

				Redirector.to("usuario").withParam("id", projeto.getGerente().getId()).redirect();

			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void cancelar() {
		Redirector.to("usuario").withParam("id", projeto.getGerente().getId()).redirect();
	}

	private boolean isValido() {
		if (Objects.isNull(projeto.getNome()) || projeto.getNome().trim().isEmpty()) {
			Messenger.messageFor("projeto-message").withMessage("Nome do projeto inválido")
					.withSeverity(FacesMessage.SEVERITY_WARN).send();
		}

		if (Objects.isNull(projeto.getDescricao()) || projeto.getDescricao().trim().isEmpty()) {
			Messenger.messageFor("projeto-message").withMessage("Descrição do projeto inválida")
					.withSeverity(FacesMessage.SEVERITY_WARN).send();
		}

		return true;
	}

	private boolean isNovo() {
		return novo;
	}

	public void pesquisaUsuarioParaAlocacao() {

		if (isPesquisaUsuarioAlocacaoValida()) {
			try {
				List<UsuarioDTO> list = Clients.pesquisaUsuarioPorNomeOuEmail(paramPesquisaUsuario).doRequest().get()
						.getUsuarios();
				list.removeIf(usuario -> projeto.getUsuariosAlocados().contains(usuario));

				this.resultPesquisaUsuarioAlocacao.clear();
				this.resultPesquisaUsuarioAlocacao.addAll(list);

			} catch (URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private boolean isPesquisaUsuarioAlocacaoValida() {

		if (Objects.isNull(paramPesquisaUsuario) || paramPesquisaUsuario.trim().isEmpty()) {

			Messenger.messageFor("alocacao-usuario-message").withMessage("Parâmetro de pesquisa inválido")
					.withSeverity(FacesMessage.SEVERITY_WARN).send();
			return false;
		}

		return true;

	}

	public void alocarUsuario(UsuarioDTO usuario) {
		projeto.getUsuariosAlocados().add(usuario);
		Messenger.messageFor("alocacao-usuario-message").withMessage("Usuario alocado")
				.withSeverity(FacesMessage.SEVERITY_INFO).send();
	}

	public void desalocarUsuario(UsuarioDTO usuario) {
		projeto.getUsuariosAlocados().remove(usuario);
	}
}

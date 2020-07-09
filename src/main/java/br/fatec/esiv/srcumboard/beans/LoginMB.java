package br.fatec.esiv.srcumboard.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import br.fatec.esiv.srcumboard.cache.Cache;
import br.fatec.esiv.srcumboard.client.Clients;
import br.fatec.esiv.srcumboard.dto.UsuarioDTO;
import br.fatec.esiv.srcumboard.form.UsuarioForm;
import br.fatec.esiv.srcumboard.util.Messenger;
import br.fatec.esiv.srcumboard.util.Redirector;

@SuppressWarnings("serial")
@ManagedBean(name = "login")
@ViewScoped
public class LoginMB extends BaseMB implements Serializable {

	private UsuarioForm usuarioForm = new UsuarioForm();
	private UsuarioDTO usuarioLogado;

	private String usuarioLogin;
	private String senhaLogin;

	public UsuarioDTO getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(UsuarioDTO usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UsuarioForm getUsuarioForm() {
		return usuarioForm;
	}

	public void setUsuarioForm(UsuarioForm usuarioForm) {
		this.usuarioForm = usuarioForm;
	}

	public String getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(String usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getSenhaLogin() {
		return senhaLogin;
	}

	public void setSenhaLogin(String senhaLogin) {
		this.senhaLogin = senhaLogin;
	}

	public void cadastrar() {

		if (isCadastroValido(usuarioForm)) {
			try {
				Optional<UsuarioDTO> dto = Clients.cadastroNovoUsuario(usuarioForm).doRequest();
				this.usuarioLogado = dto.get();
				Cache.idUsuarioLogado = dto.get().getId();
				Redirector.to("usuario").withParam("id", dto.get().getId()).redirect();
			} catch (URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void login() {
		if (isLoginValido(usuarioLogin, senhaLogin)) {
			try {
				Optional<UsuarioDTO> dto = Clients.login(usuarioLogin, senhaLogin).doRequest();
				this.usuarioLogado = dto.get();
				Cache.idUsuarioLogado = dto.get().getId();
				Redirector.to("usuario").withParam("id", dto.get().getId()).redirect();
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
				Messenger.messageFor("login-message").withMessage("Email ou senha incorretos")
						.withSeverity(FacesMessage.SEVERITY_WARN).send();
			}
		}
	}

	private boolean isLoginValido(String email, String senha) {

		if (Objects.isNull(email) || email.trim().isEmpty()) {
			Messenger.messageFor("login-message").withMessage("Email inválido").withSeverity(FacesMessage.SEVERITY_WARN)
					.send();
			return false;
		}

		if (Objects.isNull(email) || email.trim().isEmpty()) {
			Messenger.messageFor("login-message").withMessage("Senha inválida").withSeverity(FacesMessage.SEVERITY_WARN)
					.send();
			return false;
		}

		return true;
	}

	private boolean isCadastroValido(UsuarioForm form) {

		if (Objects.isNull(form.getNome()) || form.getNome().trim().isEmpty()) {
			Messenger.messageFor("cadastro-message").withMessage("Nome inválido")
					.withSeverity(FacesMessage.SEVERITY_WARN).send();
			return false;
		}

		if (Objects.isNull(form.getEmail()) || form.getEmail().trim().isEmpty()) {
			Messenger.messageFor("cadastro-message").withMessage("Email inválido")
					.withSeverity(FacesMessage.SEVERITY_WARN).send();
			return false;
		}

		if (Objects.isNull(form.getSenha()) || form.getSenha().trim().isEmpty()) {
			Messenger.messageFor("cadastro-message").withMessage("Senha inválida")
					.withSeverity(FacesMessage.SEVERITY_WARN).send();
			return false;
		}

		return true;
	}

	public void logout() {
		this.usuarioLogado = null;
		Cache.idUsuarioLogado = null;
		Redirector.to("login").redirect();
	}

}

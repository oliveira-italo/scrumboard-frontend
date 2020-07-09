package br.fatec.esiv.srcumboard.beans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.fatec.esiv.srcumboard.cache.Cache;
import br.fatec.esiv.srcumboard.client.Clients;
import br.fatec.esiv.srcumboard.dto.TarefaDTO;
import br.fatec.esiv.srcumboard.enuns.StatusTarefa;
import br.fatec.esiv.srcumboard.form.TarefaForm;
import br.fatec.esiv.srcumboard.util.Messenger;
import br.fatec.esiv.srcumboard.util.Redirector;

@ManagedBean(name = "tarefamb")
@ViewScoped
public class TarefaMB extends BaseMB {

	private StatusTarefa[] allStatusTarefa = StatusTarefa.values();
	private List<SelectItem> sprintsCombo = new ArrayList<>();
	private List<SelectItem> usuariosCombo = new ArrayList<>();
	private TarefaForm tarefa;
	private Long tarefaId;
	private Long idProjeto;

	private boolean souGerente = false;
	private boolean souResponsavel = false;

	public boolean isSouGerente() {
		return souGerente;
	}

	public void setSouGerente(boolean souGerente) {
		this.souGerente = souGerente;
	}

	public boolean isSouResponsavel() {
		return souResponsavel;
	}

	public void setSouResponsavel(boolean souResponsavel) {
		this.souResponsavel = souResponsavel;
	}

	public TarefaForm getTarefa() {
		return tarefa;
	}

	public void setTarefa(TarefaForm tarefa) {
		this.tarefa = tarefa;
	}

	public List<SelectItem> getSprintsCombo() {
		return sprintsCombo;
	}

	public List<SelectItem> getUsuariosCombo() {
		return usuariosCombo;
	}

	public StatusTarefa[] getAllStatusTarefa() {
		return allStatusTarefa;
	}

	@PostConstruct
	public void init() {

		this.tarefa = new TarefaForm();

		try {
			this.idProjeto = Long.parseLong(getUrlParameter("idProjeto").get());

			this.usuariosCombo = Clients.carregarProjeto(idProjeto).doRequest().get().getUsuariosAlocados().stream()
					.map(usuario -> new SelectItem(usuario.getId(), usuario.getNome())).collect(Collectors.toList());
			this.sprintsCombo = Clients.carregarSprintsPorProjeto(idProjeto).doRequest().get().getSprints().stream()
					.map(sprint -> new SelectItem(sprint.getId(), sprint.getNome())).collect(Collectors.toList());

			this.souGerente = Clients.carregarProjeto(idProjeto).doRequest().get().getGerente().getId()
					.equals(Cache.idUsuarioLogado);

			Optional<String> idTarefa = getUrlParameter("id");
			if (idTarefa.isPresent()) {
				this.tarefaId = Long.parseLong(idTarefa.get());
				TarefaDTO tarefaDTO = Clients.carregarTarefa(tarefaId).doRequest().get();
				populaFormulario(tarefaDTO);
				this.souResponsavel = Objects.isNull(tarefaDTO.getUsuario()) ? false
						: tarefaDTO.getUsuario().getId().equals(Cache.idUsuarioLogado);
			}

		} catch (IOException | URISyntaxException | NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	private void populaFormulario(TarefaDTO dto) {
		TarefaForm form = new TarefaForm();
		form.setNome(dto.getNome());
		form.setDescricao(dto.getDescricao());
		form.setDuracaoPrevistaEmHoras(dto.getDuracaoPrevistaEmHoras());
		form.setSprintId(dto.getSprint().getId());
		form.setUsuarioId(Objects.isNull(dto.getUsuario()) ? null : dto.getUsuario().getId());
		form.setStatus(dto.getStatus());

		this.tarefa = form;

	}

	public void salvarTarefa() {

		try {
			if (isTarefaValida()) {
				if (isNovaTarefa()) {
					Clients.cadastrarTarefa(tarefa).doRequest();
				} else {
					Clients.atualizarTarefa(tarefa, tarefaId).doRequest();
				}

				Redirector.to("kanban").withParam("idProjeto", idProjeto).withParam("idSprint", tarefa.getSprintId())
						.redirect();

			}
		} catch (IOException | URISyntaxException ex) {
			ex.printStackTrace();
		}
	}

	public void cancelar() {
		Redirector.to("kanban").withParam("idProjeto", idProjeto).withParam("idSprint", tarefa.getSprintId())
				.redirect();
	}

	private boolean isNovaTarefa() {
		return Objects.isNull(tarefaId);
	}

	private boolean isTarefaValida() {

		if (Objects.isNull(tarefa.getNome()) || tarefa.getNome().trim().isEmpty()) {
			Messenger.messageFor("tarefa-message").withMessage("Nome da tarefa invaĺido")
					.withSeverity(FacesMessage.SEVERITY_WARN).send();
			return false;
		}

		return true;

	}

}

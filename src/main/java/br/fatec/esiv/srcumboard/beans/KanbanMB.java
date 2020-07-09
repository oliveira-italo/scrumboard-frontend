package br.fatec.esiv.srcumboard.beans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.http.client.ClientProtocolException;

import br.fatec.esiv.srcumboard.cache.Cache;
import br.fatec.esiv.srcumboard.client.Clients;
import br.fatec.esiv.srcumboard.dto.ProjetoDTO;
import br.fatec.esiv.srcumboard.dto.SprintDTO;
import br.fatec.esiv.srcumboard.dto.TarefaDTO;
import br.fatec.esiv.srcumboard.enuns.StatusTarefa;
import br.fatec.esiv.srcumboard.util.Redirector;

@ManagedBean(name = "kanbanmb")
@ViewScoped
public class KanbanMB extends BaseMB {

	private ProjetoDTO projeto = new ProjetoDTO();
	private List<SelectItem> sprintsCombo = new ArrayList<>();
	private Long selectedSprintCombo;

	private SprintDTO sprint = new SprintDTO();

	private List<TarefaDTO> backlog = new ArrayList<>();
	private List<TarefaDTO> emDesenvolvimento = new ArrayList<>();
	private List<TarefaDTO> emTeste = new ArrayList<>();
	private List<TarefaDTO> bloqueada = new ArrayList<>();
	private List<TarefaDTO> prontaParaProducao = new ArrayList<>();
	private List<TarefaDTO> emProducao = new ArrayList<>();
	private List<TarefaDTO> cancelada = new ArrayList<>();

	private boolean souGerente = false;

	public boolean isSouGerente() {
		return souGerente;
	}

	public void setSouGerente(boolean souGerente) {
		this.souGerente = souGerente;
	}

	public ProjetoDTO getProjeto() {
		return projeto;
	}

	public void setProjeto(ProjetoDTO projeto) {
		this.projeto = projeto;
	}

	public List<SelectItem> getSprintsCombo() {
		return sprintsCombo;
	}

	public void setSprintsCombo(List<SelectItem> sprintsCombo) {
		this.sprintsCombo = sprintsCombo;
	}

	public Long getSelectedSprintCombo() {
		return selectedSprintCombo;
	}

	public void setSelectedSprintCombo(Long selectedSprintCombo) {
		this.selectedSprintCombo = selectedSprintCombo;
	}

	public SprintDTO getSprint() {
		return sprint;
	}

	public void setSprint(SprintDTO sprint) {
		this.sprint = sprint;
	}

	public List<TarefaDTO> getBacklog() {
		return backlog;
	}

	public void setBacklog(List<TarefaDTO> backlog) {
		this.backlog = backlog;
	}

	public List<TarefaDTO> getEmDesenvolvimento() {
		return emDesenvolvimento;
	}

	public void setEmDesenvolvimento(List<TarefaDTO> emDesenvolvimento) {
		this.emDesenvolvimento = emDesenvolvimento;
	}

	public List<TarefaDTO> getEmTeste() {
		return emTeste;
	}

	public void setEmTeste(List<TarefaDTO> emTeste) {
		this.emTeste = emTeste;
	}

	public List<TarefaDTO> getBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(List<TarefaDTO> bloqueada) {
		this.bloqueada = bloqueada;
	}

	public List<TarefaDTO> getProntaParaProducao() {
		return prontaParaProducao;
	}

	public void setProntaParaProducao(List<TarefaDTO> prontaParaProducao) {
		this.prontaParaProducao = prontaParaProducao;
	}

	public List<TarefaDTO> getEmProducao() {
		return emProducao;
	}

	public void setEmProducao(List<TarefaDTO> emProducao) {
		this.emProducao = emProducao;
	}

	public List<TarefaDTO> getCancelada() {
		return cancelada;
	}

	public void setCancelada(List<TarefaDTO> cancelada) {
		this.cancelada = cancelada;
	}

	@PostConstruct
	public void init() {
		try {

			carregaProjeto();
			carregaComboSprints();
			carregaSprint();

		} catch (NumberFormatException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void carregaSprint() {
		Optional<String> urlParameter = getUrlParameter("idSprint");

		if (urlParameter.isPresent()) {
			try {
				this.selectedSprintCombo = Long.parseLong(urlParameter.get());
				this.sprint = Clients.carregarSprint(selectedSprintCombo).doRequest().get();
				carregaTarefas();
			} catch (IOException | URISyntaxException | NumberFormatException e) {
				e.printStackTrace();
			}

		} else {
			this.selectedSprintCombo = null;
			this.sprint = new SprintDTO();
		}

	}

	private void carregaTarefas() {

		if (!Objects.isNull(sprint.getId())) {

			try {
				List<TarefaDTO> tarefas = Clients.carregarTarefasBySprint(sprint.getId()).doRequest().get()
						.getTarefas();
				this.backlog = tarefas.stream().filter(tarefa -> tarefa.getStatus() == StatusTarefa.BACKLOG)
						.collect(Collectors.toList());
				this.emDesenvolvimento = tarefas.stream()
						.filter(tarefa -> tarefa.getStatus() == StatusTarefa.EM_DESENVOLVIMENTO)
						.collect(Collectors.toList());
				this.emTeste = tarefas.stream().filter(tarefa -> tarefa.getStatus() == StatusTarefa.EM_TESTE)
						.collect(Collectors.toList());
				this.bloqueada = tarefas.stream().filter(tarefa -> tarefa.getStatus() == StatusTarefa.BLOQUEADA)
						.collect(Collectors.toList());
				this.prontaParaProducao = tarefas.stream()
						.filter(tarefa -> tarefa.getStatus() == StatusTarefa.PRONTA_PARA_PRODUCAO)
						.collect(Collectors.toList());
				this.emProducao = tarefas.stream().filter(tarefa -> tarefa.getStatus() == StatusTarefa.EM_PRODUCAO)
						.collect(Collectors.toList());
				this.cancelada = tarefas.stream().filter(tarefa -> tarefa.getStatus() == StatusTarefa.CANCELADA)
						.collect(Collectors.toList());

			} catch (URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void carregaComboSprints() throws ClientProtocolException, URISyntaxException, IOException {
		this.sprintsCombo = Clients.carregarSprintsPorProjeto(projeto.getId()).doRequest().get().getSprints().stream()
				.map(sprint -> new SelectItem(sprint.getId(), sprint.getNome())).collect(Collectors.toList());
	}

	private void carregaProjeto()
			throws NumberFormatException, ClientProtocolException, URISyntaxException, IOException {
		this.projeto = Clients.carregarProjeto(Long.parseLong(getUrlParameter("idProjeto").get())).doRequest().get();

		if (projeto.getGerente().getId().equals(Cache.idUsuarioLogado)) {
			this.souGerente = true;
		} else {
			this.souGerente = false;
		}
	}

	public void novaSprint() {
		this.sprint = new SprintDTO();
		try {
			Long idSprint = Clients.cadastrarSprint(sprint.toForm(projeto.getId())).doRequest().get().getId();
			Redirector.to("kanban").withParam("idProjeto", projeto.getId()).withParam("idSprint", idSprint).redirect();

		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvarSprint() {

		try {

			Long idSprint;

			if (isNovaSprint()) {
				idSprint = Clients.cadastrarSprint(sprint.toForm(projeto.getId())).doRequest().get().getId();
			} else {
				idSprint = Clients.atualizarSprint(sprint.toForm(projeto.getId()), sprint.getId()).doRequest().get()
						.getId();
			}

			Redirector.to("kanban").withParam("idProjeto", projeto.getId()).withParam("idSprint", idSprint).redirect();

		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean isNovaSprint() {
		return Objects.isNull(sprint) || Objects.isNull(sprint.getId());
	}

	public void onSprintChange() {
		if (!Objects.isNull(selectedSprintCombo)) {
			Redirector.to("kanban").withParam("idProjeto", projeto.getId()).withParam("idSprint", selectedSprintCombo)
					.redirect();
		} else {
			Redirector.to("kanban").withParam("idProjeto", projeto.getId()).redirect();
		}
	}

	public void novaTarefa() {
		Redirector.to("tarefa").withParam("idProjeto", projeto.getId()).redirect();
	}

	public void editarTarefa(Long idTarefa) {
		Redirector.to("tarefa").withParam("idProjeto", projeto.getId()).withParam("id", idTarefa).redirect();
	}

	public void voltarParaProjetos() {
		Redirector.to("usuario").withParam("id", Cache.idUsuarioLogado).redirect();
	}

}

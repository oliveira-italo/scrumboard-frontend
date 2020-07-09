package br.fatec.esiv.srcumboard.client;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import br.fatec.esiv.srcumboard.dto.HelloWorldDTO;
import br.fatec.esiv.srcumboard.dto.ProjetoDTO;
import br.fatec.esiv.srcumboard.dto.SprintDTO;
import br.fatec.esiv.srcumboard.dto.TarefaDTO;
import br.fatec.esiv.srcumboard.dto.UsuarioDTO;
import br.fatec.esiv.srcumboard.dto.wrapper.ProjetoDTOListWrapper;
import br.fatec.esiv.srcumboard.dto.wrapper.SprintDTOWrapper;
import br.fatec.esiv.srcumboard.dto.wrapper.TarefaDTOWrapper;
import br.fatec.esiv.srcumboard.dto.wrapper.UsuarioDTOWrapper;
import br.fatec.esiv.srcumboard.form.ProjetoForm;
import br.fatec.esiv.srcumboard.form.SprintForm;
import br.fatec.esiv.srcumboard.form.TarefaForm;
import br.fatec.esiv.srcumboard.form.UsuarioForm;

public class Clients {

	public static ApiClient<HelloWorldDTO> helloWorld() {

		return new ApiClient<HelloWorldDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/helloworld";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<UsuarioDTO> cadastroNovoUsuario(UsuarioForm usuarioForm) {

		return new ApiClient<UsuarioDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpPost post = new HttpPost(uri);
				post.setHeader("Accept", "application/json");
				post.setHeader("Content-type", "application/json");
				post.setEntity(new StringEntity(usuarioForm.toJson(), StandardCharsets.UTF_8));
				return post;
			}

			@Override
			protected String getPath() {
				return "/usuario";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<UsuarioDTO> login(String email, String senha) {

		return new ApiClient<UsuarioDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpGet get = new HttpGet(uri);
				return get;
			}

			@Override
			protected String getPath() {
				return "/usuario/login";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("email", email));
				params.add(new BasicNameValuePair("senha", senha));
				return Optional.of(params);
			}
		};
	}

	public static ApiClient<UsuarioDTO> carregarUsuario(Long idUsuario) {

		return new ApiClient<UsuarioDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/usuario/".concat(idUsuario.toString());
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<ProjetoDTOListWrapper> carregarAlocacaoUsuario(Long idUsuario) {

		return new ApiClient<ProjetoDTOListWrapper>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/usuario/".concat(idUsuario.toString()).concat("/alocacao");
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};

	}

	public static ApiClient<ProjetoDTOListWrapper> carregarGerenciaUsuario(Long idUsuario) {

		return new ApiClient<ProjetoDTOListWrapper>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/usuario/".concat(idUsuario.toString()).concat("/gerencia");
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<UsuarioDTO> atualizarUsuario(UsuarioForm usuarioForm, Long idUsuario) {

		return new ApiClient<UsuarioDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpPut post = new HttpPut(uri);
				post.setHeader("Accept", "application/json");
				post.setHeader("Content-type", "application/json");
				post.setEntity(new StringEntity(usuarioForm.toJson(), StandardCharsets.UTF_8));
				return post;
			}

			@Override
			protected String getPath() {
				return "/usuario/".concat(idUsuario.toString());
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<ProjetoDTO> carregarProjeto(Long idProjeto) {

		return new ApiClient<ProjetoDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/projeto/".concat(idProjeto.toString());
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<ProjetoDTO> cadastrarProjeto(ProjetoForm form) {

		return new ApiClient<ProjetoDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpPost post = new HttpPost(uri);
				post.setHeader("Accept", "application/json");
				post.setHeader("Content-type", "application/json");
				post.setEntity(new StringEntity(form.toJson(), StandardCharsets.UTF_8));
				return post;
			}

			@Override
			protected String getPath() {
				return "/projeto";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<ProjetoDTO> atualizarProjeto(ProjetoForm form, Long idProjeto) {

		return new ApiClient<ProjetoDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpPut put = new HttpPut(uri);
				put.setHeader("Accept", "application/json");
				put.setHeader("Content-type", "application/json");
				put.setEntity(new StringEntity(form.toJson(), StandardCharsets.UTF_8));
				return put;
			}

			@Override
			protected String getPath() {
				return "/projeto/".concat(idProjeto.toString());
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<UsuarioDTOWrapper> pesquisaUsuarioPorNomeOuEmail(String param) {

		return new ApiClient<UsuarioDTOWrapper>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/usuario/pesquisa";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("param", param));
				return Optional.of(params);
			}
		};

	}

	public static ApiClient<SprintDTOWrapper> carregarSprintsPorProjeto(Long idProjeto) {

		return new ApiClient<SprintDTOWrapper>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/sprint/projeto";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("idProjeto", idProjeto.toString()));
				return Optional.of(params);
			}
		};
	}

	public static ApiClient<SprintDTO> cadastrarSprint(SprintForm form) {
		return new ApiClient<SprintDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpPost post = new HttpPost(uri);
				post.setHeader("Accept", "application/json");
				post.setHeader("Content-type", "application/json");
				post.setEntity(new StringEntity(form.toJson(), StandardCharsets.UTF_8));
				return post;
			}

			@Override
			protected String getPath() {
				return "/sprint";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<SprintDTO> atualizarSprint(SprintForm form, Long idSprint) {
		return new ApiClient<SprintDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpPut put = new HttpPut(uri);
				put.setHeader("Accept", "application/json");
				put.setHeader("Content-type", "application/json");
				put.setEntity(new StringEntity(form.toJson(), StandardCharsets.UTF_8));
				return put;
			}

			@Override
			protected String getPath() {
				return "/sprint/".concat(idSprint.toString());
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<SprintDTO> carregarSprint(Long idSprint) {

		return new ApiClient<SprintDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/sprint/".concat(idSprint.toString());
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<TarefaDTO> carregarTarefa(Long idTarefa) {

		return new ApiClient<TarefaDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/tarefa/".concat(idTarefa.toString());
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<TarefaDTO> cadastrarTarefa(TarefaForm form) {

		return new ApiClient<TarefaDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpPost post = new HttpPost(uri);
				post.setHeader("Accept", "application/json");
				post.setHeader("Content-type", "application/json");
				post.setEntity(new StringEntity(form.toJson(), StandardCharsets.UTF_8));
				return post;
			}

			@Override
			protected String getPath() {
				return "/tarefa";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};

	}

	public static ApiClient<TarefaDTO> atualizarTarefa(TarefaForm form, Long idTarefa) {

		return new ApiClient<TarefaDTO>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				HttpPut put = new HttpPut(uri);
				put.setHeader("Accept", "application/json");
				put.setHeader("Content-type", "application/json");
				put.setEntity(new StringEntity(form.toJson(), StandardCharsets.UTF_8));
				return put;
			}

			@Override
			protected String getPath() {
				return "/tarefa/".concat(idTarefa.toString());
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				return Optional.empty();
			}
		};
	}

	public static ApiClient<TarefaDTOWrapper> carregarTarefasBySprint(Long idSprint) {

		return new ApiClient<TarefaDTOWrapper>() {

			@Override
			protected HttpUriRequest getRequest(URI uri) {
				return new HttpGet(uri);
			}

			@Override
			protected String getPath() {
				return "/tarefa/sprint";
			}

			@Override
			protected Optional<List<NameValuePair>> getParameters() {
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("idSprint", idSprint.toString()));
				return Optional.of(params);
			}
		};

	}
}

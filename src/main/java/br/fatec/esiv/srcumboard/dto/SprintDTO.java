package br.fatec.esiv.srcumboard.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

import br.fatec.esiv.srcumboard.form.SprintForm;

public class SprintDTO {

	private Long id;
	private Integer ordem;
	private String nome;
	private LocalDate inicio;
	private LocalDate fim;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFim() {
		return fim;
	}

	public void setFim(LocalDate fim) {
		this.fim = fim;
	}

	public SprintForm toForm(Long idProjeto) {
		SprintForm form = new SprintForm();
		form.setProjetoId(idProjeto);
		form.setInicio(inicio);
		form.setFim(fim);
		return form;
	}
	
	@Override
	public String toString() {
		String string = "";
		if(!Objects.isNull(nome)) {
			string = string.concat(nome);
		}
		
		if(!Objects.isNull(inicio)) {
			string = string.concat(" inicio: ");
			string = string.concat(inicio.format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()));
		}
		
		if(!Objects.isNull(fim)) {
			string = string.concat(" término: ");
			string = string.concat(fim.format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()));
		}
		
		return string;
	}

}

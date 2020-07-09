package br.fatec.esiv.srcumboard.form;

import java.time.LocalDate;

public class SprintForm extends EntityForm {

	private LocalDate inicio;
	private LocalDate fim;
	private Long projetoId;

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

	public Long getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(Long projetoId) {
		this.projetoId = projetoId;
	}

}

package br.fatec.esiv.srcumboard.dto.wrapper;

import java.util.ArrayList;
import java.util.List;

import br.fatec.esiv.srcumboard.dto.TarefaDTO;

public class TarefaDTOWrapper {

	private List<TarefaDTO> tarefas = new ArrayList<>();

	public List<TarefaDTO> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<TarefaDTO> tarefas) {
		this.tarefas = tarefas;
	}

}

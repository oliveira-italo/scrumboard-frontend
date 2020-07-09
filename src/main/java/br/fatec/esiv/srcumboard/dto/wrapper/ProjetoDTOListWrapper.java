package br.fatec.esiv.srcumboard.dto.wrapper;

import java.util.ArrayList;
import java.util.List;

import br.fatec.esiv.srcumboard.dto.ProjetoDTO;

public class ProjetoDTOListWrapper {

	private List<ProjetoDTO> projetos = new ArrayList<>();

	public List<ProjetoDTO> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<ProjetoDTO> projetos) {
		this.projetos = projetos;
	}

}

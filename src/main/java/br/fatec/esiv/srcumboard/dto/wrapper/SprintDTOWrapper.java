package br.fatec.esiv.srcumboard.dto.wrapper;

import java.util.ArrayList;
import java.util.List;

import br.fatec.esiv.srcumboard.dto.SprintDTO;

public class SprintDTOWrapper {

	private List<SprintDTO> sprints = new ArrayList<>();

	public List<SprintDTO> getSprints() {
		return sprints;
	}

	public void setSprints(List<SprintDTO> sprints) {
		this.sprints = sprints;
	}

}

package br.fatec.esiv.srcumboard.dto.wrapper;

import java.util.ArrayList;
import java.util.List;

import br.fatec.esiv.srcumboard.dto.UsuarioDTO;

public class UsuarioDTOWrapper {

	private List<UsuarioDTO> usuarios = new ArrayList<>();

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

}

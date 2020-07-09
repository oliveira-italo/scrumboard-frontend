package br.fatec.esiv.srcumboard.beans;

import java.util.Optional;

import javax.faces.context.FacesContext;

public abstract class BaseMB {

	protected BaseMB() {

	}

	protected Optional<String> getUrlParameter(String key) {
		return Optional
				.ofNullable(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
	}

}

package br.fatec.esiv.srcumboard.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HelloWorldDTO implements Serializable {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

package br.fatec.esiv.srcumboard.util;

import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Messenger {

	private String messageFor;
	private String message;
	private Optional<FacesMessage.Severity> severity;

	public Messenger(String messageFor) {
		this.messageFor = messageFor;
	}

	public static Messenger messageFor(String messageFor) {
		return new Messenger(messageFor);
	}

	public Messenger withMessage(String message) {
		this.message = message;
		return this;
	}

	public Messenger withSeverity(FacesMessage.Severity severity) {
		this.severity = Optional.ofNullable(severity);
		return this;
	}

	public void send() {
		FacesContext.getCurrentInstance().addMessage(messageFor,
				new FacesMessage(severity.isPresent() ? severity.get() : FacesMessage.SEVERITY_INFO, message, ""));
	}

}

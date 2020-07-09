package br.fatec.esiv.srcumboard.beans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.fatec.esiv.srcumboard.client.Clients;
import br.fatec.esiv.srcumboard.dto.HelloWorldDTO;

@ManagedBean(name = "helloworld")
@ViewScoped
public class HelloWorldMB {

	private String message;

	@PostConstruct
	public void init() {
		this.message = "Fuck off";
	}

	public String getMessage() {
		return message;
	}

	public void consumeApi() {

		try {
			Optional<HelloWorldDTO> dto = Clients.helloWorld().doRequest();
			if(dto.isPresent()) {
				this.message = dto.get().getMessage();
			}
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

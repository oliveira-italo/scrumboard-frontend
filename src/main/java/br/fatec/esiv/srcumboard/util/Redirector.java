package br.fatec.esiv.srcumboard.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;

public class Redirector {

	private String page;
	private Map<String, String> parameters = new HashMap<>();

	private Redirector(String pageToRedirect) {
		if (!pageToRedirect.endsWith(".xhtml")) {
			pageToRedirect = pageToRedirect.concat(".xhtml");
		}
		this.page = pageToRedirect;
	}

	public static Redirector to(String pageToRedirect) {
		return new Redirector(pageToRedirect);
	}

	public Redirector withParam(String key, Object value) {
		parameters.put(key, value.toString());
		return this;
	}

	public void redirect() {

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(buildUrl());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String buildUrl() {

		String url = page;

		if (!parameters.isEmpty()) {

			url = url.concat("?");

			List<String> paramsList = parameters.entrySet().stream().map(entry -> {
				String paramUrl = entry.getKey() + "=" + entry.getValue();
				return paramUrl;
			}).collect(Collectors.toList());

			url = url.concat(String.join("&", paramsList));

		}

		return url;

	}

}

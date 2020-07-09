package br.fatec.esiv.srcumboard.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("local-date-converter")
public class LocalDateConverter implements Converter {

	private DateTimeFormatter formatter;

	public LocalDateConverter() {
		this.formatter = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if (Objects.isNull(value) || value.trim().isEmpty()) {
			return null;
		}

		try {
			return LocalDate.parse(value, formatter);
		} catch (Exception ex) {
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (Objects.isNull(value)) {
			return "";
		}

		if (LocalDate.class.isAssignableFrom(value.getClass())) {
			try {
				return ((LocalDate) value).format(formatter);
			} catch (Exception ex) {
				return "";
			}
		}

		return "";
	}

}

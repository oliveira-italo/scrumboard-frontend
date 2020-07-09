package br.fatec.esiv.srcumboard.form;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public abstract class EntityForm {

	protected EntityForm() {

	}

	public String toJson() {
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {

			@Override
			public JsonElement serialize(LocalDate data, Type typeOfSrc, JsonSerializationContext context) {
				return data == null ? null
						: new JsonPrimitive(
								data.format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter()));
			}
		}).create();
		
		String json = gson.toJson(this);
		return json;
	}

}

package com.rssl.phizic.utils.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Gulov
 * @ created 04.12.2012
 * @ $Author$
 * @ $Revision$
 */
public final class DefaultCalendarTypeAdapter implements JsonSerializer<Calendar>, JsonDeserializer<Calendar>
{
	private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public JsonElement serialize(Calendar src, Type typeOfSrc, JsonSerializationContext context) throws JsonParseException
	{
		String dateFormatAsString = formatter.format(src);
        return new JsonPrimitive(dateFormatAsString);
	}

	public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		if (!(json instanceof JsonPrimitive))
			throw new JsonParseException("Дата должна быть строкой");
		return deserializeToCalendar(json);
	}

	private Calendar deserializeToCalendar(JsonElement json)
	{
		try
		{
			Calendar result = Calendar.getInstance();
			result.setTime(formatter.parse(json.getAsString()));
			return result;
		}
		catch (ParseException e)
		{
			throw new JsonSyntaxException("Невозможно десериализовать " + json.getAsString(), e);
		}
	}
}

package com.rssl.phizic.utils.json;

import com.google.gson.*;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

import java.lang.reflect.Type;

/**
 * @author Erkin
 * @ created 14.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сериализует и десериализует номер телефона
 */
public class PhoneNumberTypeAdapter implements JsonSerializer<PhoneNumber>, JsonDeserializer<PhoneNumber>
{
	private static final PhoneNumberFormat PHONE_NUMBER_FORMAT = PhoneNumberFormat.MOBILE_INTERANTIONAL;

	public JsonElement serialize(PhoneNumber src, Type typeOfSrc, JsonSerializationContext context)
	{
		return new JsonPrimitive(PHONE_NUMBER_FORMAT.format(src));
	}

	public PhoneNumber deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		try
		{
			return PHONE_NUMBER_FORMAT.parse(json.getAsString());
		}
		catch (NumberFormatException e)
		{
			throw new JsonParseException(e);
		}
	}
}

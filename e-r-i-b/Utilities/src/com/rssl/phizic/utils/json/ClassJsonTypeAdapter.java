package com.rssl.phizic.utils.json;

import com.google.gson.*;
import com.rssl.phizic.utils.ClassHelper;

import java.lang.reflect.Type;

/**
 * @author Erkin
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * —ериализует и десериализует класс
 */
public class ClassJsonTypeAdapter implements JsonSerializer<Class>, JsonDeserializer<Class>, InstanceCreator<Class>
{
	public JsonElement serialize(Class src, Type typeOfSrc, JsonSerializationContext context)
	{
		return new JsonPrimitive(src.getName());
	}

	public Class deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		try
		{
			return ClassHelper.loadClass(json.getAsString());
		}
		catch (IllegalStateException e)
		{
			throw new JsonParseException(" ласс должен быть представлен json-строкой", e);
		}
		catch (ClassNotFoundException e)
		{
			throw new JsonParseException(e);
		}
	}

	public Class createInstance(Type type)
	{
		return (Class) type;
	}
}

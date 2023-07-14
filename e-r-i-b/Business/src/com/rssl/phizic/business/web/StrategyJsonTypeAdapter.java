package com.rssl.phizic.business.web;

import com.google.gson.*;
import com.rssl.phizic.utils.ClassHelper;

import java.lang.reflect.Type;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Json-Адаптер для объектов-стратегий
 */
public class StrategyJsonTypeAdapter implements JsonSerializer, JsonDeserializer, InstanceCreator
{
	public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context)
	{
		return new JsonPrimitive(src.getClass().getName());
	}

	public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		String className;
		try
		{
			className = json.getAsString();
		}
		catch (IllegalStateException e)
		{
			throw new JsonParseException("Объект-стратегия должна быть представлена json-строкой", e);
		}

		try
		{
			return ClassHelper.newInstance(className);
		}
		catch (RuntimeException e)
		{
			throw new JsonParseException("Не удалось создать объект-стратегию " + className, e);
		}
	}

	public Object createInstance(Type type)
	{
		Class clazz = (Class) type;

		try
		{
			return clazz.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}

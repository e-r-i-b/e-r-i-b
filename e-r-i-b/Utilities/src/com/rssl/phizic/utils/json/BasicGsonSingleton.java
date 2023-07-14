package com.rssl.phizic.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * Базовый синглтон для json-сериализации
 * @author Rtischeva
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class BasicGsonSingleton
{
	private static final BasicGsonSingleton instance = new BasicGsonSingleton();

	private final Gson gson;

	private BasicGsonSingleton()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Class.class, new ClassJsonTypeAdapter());
		gsonBuilder.registerTypeAdapter(PhoneNumber.class, new PhoneNumberTypeAdapter());
		gson = gsonBuilder.create();
	}

	public static Gson getGson()
	{
		return instance.gson;
	}
}

package com.rssl.phizic.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * @author Moshenko
 * @ created 28.01.2014
 * @ $Authors$
 * @ $Revision$
 */
public class NoPrettyPrintingGsonSingleton
{
	private static final NoPrettyPrintingGsonSingleton instance = new NoPrettyPrintingGsonSingleton();

	private final Gson gson;

	private NoPrettyPrintingGsonSingleton()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Class.class, new ClassJsonTypeAdapter());
		gsonBuilder.registerTypeAdapter(Currency.class, new CurrencyTypeAdapter());
		gsonBuilder.addDeserializationExclusionStrategy(new ExclusionStrategyImpl());
		gsonBuilder.addSerializationExclusionStrategy(new ExclusionStrategyImpl());
		gson = gsonBuilder.create();
	}

	public static Gson getGson()
	{
		return instance.gson;
	}
}

package com.rssl.phizic.security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rssl.phizic.utils.json.ClassJsonTypeAdapter;
import com.rssl.phizic.utils.json.PhoneNumberTypeAdapter;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * @author Erkin
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */
class ConfirmGsonSingleton
{
	private static final ConfirmGsonSingleton instance = new ConfirmGsonSingleton();

	private final Gson gson;

	///////////////////////////////////////////////////////////////////////////

	private ConfirmGsonSingleton()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Class.class, new ClassJsonTypeAdapter());
		gsonBuilder.registerTypeAdapter(PhoneNumber.class, new PhoneNumberTypeAdapter());
		gson = gsonBuilder.create();
	}

	static Gson getGson()
	{
		return instance.gson;
	}
}

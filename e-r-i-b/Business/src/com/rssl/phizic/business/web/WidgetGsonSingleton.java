package com.rssl.phizic.business.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rssl.phizic.business.web.widget.strategy.WidgetAccessor;
import com.rssl.phizic.business.web.widget.strategy.WidgetInitializer;
import com.rssl.phizic.utils.json.ClassJsonTypeAdapter;
import com.rssl.phizic.utils.json.DefaultCalendarTypeAdapter;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 13.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class WidgetGsonSingleton
{
	private static final WidgetGsonSingleton instance = new WidgetGsonSingleton();

	private final Gson gson;

	private WidgetGsonSingleton()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Calendar.class, new DefaultCalendarTypeAdapter());
		gsonBuilder.registerTypeAdapter(WidgetInitializer.class, new StrategyJsonTypeAdapter());
		gsonBuilder.registerTypeAdapter(WidgetAccessor.class, new StrategyJsonTypeAdapter());
		gsonBuilder.registerTypeAdapter(Class.class, new ClassJsonTypeAdapter());
		gson = gsonBuilder.create();
	}

	public static Gson getGson()
	{
		return instance.gson;
	}
}

package com.rssl.phizic.business.web;

import com.google.gson.*;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Barinov
 * @ created 27.06.2012
 * @ $Author$
 * @ $Revision$
 */
class WidgetSerializer
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final JsonParser jsonParser = new JsonParser();

	private static final Gson layoutGson;

	static
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		layoutGson = gsonBuilder.create();
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Сериализовать виджет
	 * @param widget виджет
	 * @return виджет в виде json-строки
	 */
	String serializeWidget(Widget widget)
	{
		return serializeObject(widget);
	}

	String serializeDefinition(WidgetDefinition definition)
	{
		return serializeObject(definition);
	}

	private String serializeObject(Object object)
	{
		Gson gson = WidgetGsonSingleton.getGson();
		return gson.toJson(object);
	}

	Widget deserializeWidget(WidgetDefinition definition, String widgetJsonString)
	{
		try
		{
			Gson gson = WidgetGsonSingleton.getGson();
			Class<? extends Widget> wigetClass = definition.getWidgetClass();
			Widget widget = gson.fromJson(widgetJsonString, wigetClass);
			widget.setDefinition(definition);
			return widget;
		}
		catch (JsonSyntaxException e)
		{
			throw new IllegalArgumentException("Не удалось десериализовать json-строку с виджетом: " + widgetJsonString, e);
		}
	}

	private Widget deserializeWidget(WidgetDefinition definition, JsonElement widgetJson)
	{
		try
		{
			Gson gson = WidgetGsonSingleton.getGson();
			Class<? extends Widget> wigetClass = definition.getWidgetClass();
			Widget widget = gson.fromJson(widgetJson, wigetClass);
			widget.setDefinition(definition);
			return widget;
		}
		catch (JsonSyntaxException e)
		{
			throw new IllegalArgumentException("Не удалось десериализовать json с виджетом: " + widgetJson, e);
		}
	}

	List<WebPage> deserializeWebPageList(String pagesString, Map<String, WidgetDefinition> definitions)
	{
		JsonArray pagesJson;
		try
		{
			JsonElement jsonElement = jsonParser.parse(pagesString);
			pagesJson = jsonElement.getAsJsonArray();
		}
		catch (JsonSyntaxException e)
		{
			throw new IllegalArgumentException("Сбой разбора json-строки со списком виджет-контейнеров " + pagesString, e);
		}

		try
		{
			List<WebPage> pages = new ArrayList<WebPage>(pagesJson.size());
			for (JsonElement element : pagesJson)
			{
				JsonObject pageJson = element.getAsJsonObject();
				WebPage page = readWebPageJson(definitions, pageJson);
				pages.add(page);
			}
			return pages;
		}
		catch (RuntimeException e)
		{
			throw new IllegalArgumentException("Ошибка анализа json-строки со списком виджет-контейнеров " + pagesString, e);
		}
	}

	private WebPage readWebPageJson(Map<String, WidgetDefinition> definitions, JsonObject webPageJson)
	{
		WebPage page = new WebPage();
		page.setClassname(webPageJson.get("codename").getAsString());
		page.setLocation(Location.valueOf(webPageJson.get("location").getAsString()));
		page.setLayout(deserializeLayout(webPageJson.get("layout").getAsJsonArray()));

		JsonArray widgetListJson = webPageJson.get("widgets").getAsJsonArray();
		for (JsonElement element : widgetListJson)
		{
			JsonObject definitionJson = element.getAsJsonObject().get("definition").getAsJsonObject();
			String definitionCodename = definitionJson.get("codename").getAsString();
			WidgetDefinition definition = definitions.get(definitionCodename);
			if (definition == null) {
				log.error("Не найдена виджет-дефиниция " + definitionCodename);
				continue;
			}

			Widget widget = deserializeWidget(definition, element);
			if (widget != null)
				page.addWidget(widget);
		}

		return page;
	}

	String serializeLayout(Layout layout)
	{
		return layoutGson.toJson(layout.getElements());
	}

	Layout deserializeLayout(String layoutAsString)
	{
		JsonElement jsonElement = jsonParser.parse(layoutAsString);
		JsonArray jsonArray = jsonElement.getAsJsonArray();
		return deserializeLayout(jsonArray);
	}

	private Layout deserializeLayout(JsonArray layoutAsJsonArray)
	{
		//noinspection unchecked
		List<Object> elements = layoutGson.fromJson(layoutAsJsonArray, List.class);
		return new Layout(elements);
	}
}

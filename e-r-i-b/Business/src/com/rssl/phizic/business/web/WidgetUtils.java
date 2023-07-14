package com.rssl.phizic.business.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 13.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class WidgetUtils
{
	public static Map<String, WebPage> mapWebPagesByClassname(Collection<WebPage> collection)
	{
		if (CollectionUtils.isEmpty(collection))
			return new HashMap<String, WebPage>();

		Map<String, WebPage> pages = new HashMap<String, WebPage>(collection.size());
		for (WebPage page : collection)
			pages.put(page.getClassname(), page);

		return pages;
	}

	public static Map<String, WidgetDefinition> mapWidgetDefinitionsByCodename(Collection<WidgetDefinition> collection)
	{
		if (CollectionUtils.isEmpty(collection))
			return new HashMap<String, WidgetDefinition>();

		Map<String, WidgetDefinition> map = new HashMap<String, WidgetDefinition>(collection.size());
		for (WidgetDefinition definition : collection)
			map.put(definition.getCodename(), definition);

		return map;
	}

	public static Map<String, WebPage> mixWebPageMap(Map<String, WebPage> defaultPages, Map<String, WebPage> savedPages)
	{
		if (MapUtils.isEmpty(defaultPages))
			return savedPages;
		if (MapUtils.isEmpty(savedPages))
			return defaultPages;

		Map<String, WebPage> pages = new HashMap<String, WebPage>(defaultPages);
		for (Map.Entry<String, WebPage> entry : pages.entrySet()) {
			WebPage savedPage = savedPages.get(entry.getKey());
			if (savedPage != null)
				entry.setValue(savedPage);
		}
		return pages;
	}

	public static Map<String, WebPage> cloneWebPageMap(Map<String, WebPage> pages)
	{
		if (MapUtils.isEmpty(pages))
			return Collections.emptyMap();

		Map<String, WebPage> copies = new HashMap<String, WebPage>(pages.size());
		for (Map.Entry<String, WebPage> entry : pages.entrySet())
			copies.put(entry.getKey(), entry.getValue().clone());

		return copies;
	}

	/**
	 * Выполняет слияние пользовательских настроек описаний виджетов
	 * @param definitions - итоговый список
	 * @param oldDefinitions - список, настройки из которого нужно вытащить и сохранить в итоговом списке
	 */
	public static void mergeDefinitions(List<WidgetDefinition> definitions, List<WidgetDefinition> oldDefinitions)
	{
		Map<String, WidgetDefinition> oldDefinitionsMap = new HashMap<String, WidgetDefinition>(oldDefinitions.size());
		for(WidgetDefinition oldDefinition : oldDefinitions)
		{
			oldDefinitionsMap.put(oldDefinition.getCodename(), oldDefinition);
		}

		for(WidgetDefinition definition : definitions)
		{
			String codename = definition.getCodename();
			if(oldDefinitionsMap.containsKey(codename))
				definition.merge(oldDefinitionsMap.get(codename));
		}

		Collections.sort(definitions,new WidgetDefinitionComparator());

		//Т.к. в definitions теперь могут сожержаться элементы с одинаковыми индексами или какие-то индексы могут пропускаться,
		//устанавливаем новые индексы, проходя по отсортированному списку
		long i = 1L;
		for(WidgetDefinition definition : definitions)
		{
			definition.setIndex(i);
			i++;
		}
	}
}

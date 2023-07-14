package com.rssl.phizic.business.web;

/**
 * @author Gulov
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Дефиниция виджета, дефиниция в виде строки и признака новый он или нет
 */
@SuppressWarnings({"JavaDoc"})
public class PersonWidgetDefinition
{
	private final WidgetDefinition definition;
	private final String definitionAsJson;
	private final boolean novelty;

	public PersonWidgetDefinition(WidgetDefinition definition, String definitionAsJson, boolean novelty)
	{
		this.definition = definition;
		this.definitionAsJson = definitionAsJson;
		this.novelty = novelty;
	}

	public WidgetDefinition getDefinition()
	{
		return definition;
	}

	public String getDefinitionAsJson()
	{
		return definitionAsJson;
	}

	public boolean isNovelty()
	{
		return novelty;
	}
}

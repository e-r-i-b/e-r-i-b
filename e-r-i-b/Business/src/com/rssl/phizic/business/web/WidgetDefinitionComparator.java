package com.rssl.phizic.business.web;

import java.util.Comparator;

/**
 * User: moshenko
 * Date: 07.12.2012
 * Time: 10:44:04
 * Компаратор для определения порядка отображения  WidgetDefinition
 */
public class WidgetDefinitionComparator implements Comparator<WidgetDefinition>
{

	public int compare(WidgetDefinition o1, WidgetDefinition o2)
	{
		return o1.getIndex().compareTo(o2.getIndex());
	}
}

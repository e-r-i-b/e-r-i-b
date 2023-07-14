package com.rssl.phizic.web.util;

import org.apache.commons.lang.StringUtils;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.business.web.WidgetService;

/**
 * @author Erkin
 * @ created 30.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��� ������������� � jsp ��������
 */
public class WidgetFunctions
{
	private static final WidgetService widgetService = new WidgetService();
	/**
	 * ����������� ������ � ����������� ������� � ����, ���������� ��� �������������
	 * � �������� data-dojo-props ��������� ���� �������
	 * - ������� ������� ������� � �����
	 * - �������� ������� ������� �� ������
	 * - �������� ������� ������� ����������
	 * - ������� �������� �������
	 * @param props - ��������� ������� � ���� json-������� 
	 * @return
	 */
	public static String escapeDataDojoProps(String props)
	{
		String result = StringUtils.trimToEmpty(props);
		result = StringUtils.replaceChars(result, "\n\r\"", "  '");
		result = StringUtils.stripEnd(result, ",");
		return result;
	}

	/**
	 * ����������� �������� � ������� � ������ Json
	 * @param definition - ��������� �������
	 * @return - ������ Json
	 */
	public static String definitionAsJson(WidgetDefinition definition)
	{
		return widgetService.getDefinitionAsJson(definition);
	}
}

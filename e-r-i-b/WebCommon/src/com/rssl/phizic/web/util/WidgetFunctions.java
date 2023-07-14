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
 * Функции для использования в jsp виджетов
 */
public class WidgetFunctions
{
	private static final WidgetService widgetService = new WidgetService();
	/**
	 * Конверитует строку с параметрами виджета к виду, пригодному для использования
	 * в атрибуте data-dojo-props корневого тега виджета
	 * - удаляет пробелы спереди и сзади
	 * - заменяет перевод каретки на пробел
	 * - заменяет двойные ковычки одинарными
	 * - удаляет хвоствую запятую
	 * @param props - параметры виджета в виде json-объекта 
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
	 * Преобразует дефиницю в виджета в строку Json
	 * @param definition - дефиниция виджета
	 * @return - строка Json
	 */
	public static String definitionAsJson(WidgetDefinition definition)
	{
		return widgetService.getDefinitionAsJson(definition);
	}
}

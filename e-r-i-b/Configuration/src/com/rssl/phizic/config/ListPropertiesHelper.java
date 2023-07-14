package com.rssl.phizic.config;

import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * хелпер для работы с табличными настройками содержащих два поля
 * @author gladishev
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ListPropertiesHelper
{
	protected String idKey; //код ключевого поля
	private String codeKey; //код отображаемого клиенту поля
	protected String prefixValue; //префикс для значений
	protected String delimeter; //разделитель значений настройки
	private String regex; //регулярное выражение применяемое при поиске подстрок. обчыно равняется значению delimeter
	                      //можно задать их различными для сложных случаев (пример "\\^").

	/**
	 * Конструктор
	 * @param idKey - код ключевого поля
	 * @param codeKey - код отображаемого клиенту поля
	 * @param prefixValue - префикс для значений
	 * @param delimeter - разделитель значений настройки
	 */
	public ListPropertiesHelper(String idKey, String codeKey, String prefixValue, String delimeter)
	{
		this.idKey = idKey;
		this.codeKey = codeKey;
		this.prefixValue = prefixValue;
		this.delimeter = delimeter;
		this.regex = delimeter;
	}

	/**
	 * Конструктор
	 * @param idKey - код ключевого поля
	 * @param codeKey - код отображаемого клиенту поля
	 * @param delimeter - строка применяемая для разделения значений
	 * @param regex - регулярное выражение применяемое при поиске подстрок в стоке
	 */
	public ListPropertiesHelper(String idKey, String codeKey, String prefixValue, String delimeter, String regex)
	{
		this.idKey = idKey;
		this.codeKey = codeKey;
		this.prefixValue = prefixValue;
		this.delimeter = delimeter;
		this.regex = regex;
	}

	public Map<String, String> convertToProperties(String property)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (StringHelper.isEmpty(property))
			return result;
		String[] values = property.split(this.regex);
		for (int i=0; i<values.length; i++)
		{
			result.put(idKey + i, Integer.toString(i));
			result.put(codeKey + i, values[i]);
		}
		return result;
	}

	public Map<String, String> convertToMap(List<Property> properties)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (CollectionUtils.isEmpty(properties))
			return result;
		int i = 0;
		for (Property property : properties)
		{
			result.put(idKey + i, Integer.toString(i));
			result.put(codeKey + i, property.getValue().substring(prefixValue.length()));
			i++;
		}
		return result;
	}

	public String convertToString(Map<String, Object> properties)
	{
		if (MapUtils.isEmpty(properties))
			return "";
		
		List<String> valies = new ArrayList<String>();

		List<Integer> indexes = PropertyHelper.getTableSettingNumbers(properties, codeKey);
		for (Integer index : indexes)
		{
			valies.add((String) properties.remove(codeKey + index.toString()));
		}

		return org.apache.commons.lang.StringUtils.join(valies, this.delimeter);
	}

	public List<String> convertToList(Map<String, Object> properties)
	{
		List<String> list = new ArrayList<String>();
		if (MapUtils.isEmpty(properties))
			return list;

		List<Integer> indexes = PropertyHelper.getTableSettingNumbers(properties, codeKey);
		for (Integer index : indexes)
		{
			list.add(prefixValue + properties.remove(codeKey + index.toString()).toString());
		}

		return list;
	}
}
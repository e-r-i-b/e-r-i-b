package com.rssl.phizic.config;

import com.rssl.phizic.utils.PropertyHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * * хелпер дл€ работы с табличными настройками с несколькими пол€ми в строках
 * @ author gorshkov
 * @ created 25.03.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiFieldsListPropertiesHelper extends ListPropertiesHelper
{
	private Set<String> codeKeys; //код полей из которых формируетс€ значение

	/**
	 *  онструктор
	 * @param codeKeys - код полей из которых формируетс€ значение
	 */
	public MultiFieldsListPropertiesHelper(String idKey, String codeKey, Set<String> codeKeys, String prefixValue, String delimeter)
	{
		super(idKey, codeKey, prefixValue, delimeter);
		this.codeKeys = codeKeys;
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
			String[] values = property.getValue().split(this.delimeter);
			int j = 0;
			for (String code : codeKeys)
			{
				result.put(code + i, values[j].substring(prefixValue.length()));
				j++;
			}
			i++;
		}
		return result;
	}

	public List<String> convertToList(Map<String, Object> properties)
	{
		List<String> list = new ArrayList<String>();
		if (MapUtils.isEmpty(properties))
			return list;

		String key = new ArrayList<String>(codeKeys).get(0);
		List<Integer> indexes = PropertyHelper.getTableSettingNumbers(properties, key);
		for (Integer index : indexes)
		{
			String value = prefixValue;
			int j=0;
			for (String code : codeKeys)
			{
				value = value + (j>0 ? delimeter : "") + properties.remove(code + index.toString()).toString();
				j++;
			}
			list.add(value);
		}
		return list;
	}

}

package com.rssl.common.forms.processing;

import org.apache.commons.collections.MapUtils;

import java.util.Map;
import java.util.HashMap;

/**
 * Истоник данных, заменяющий определенные значения на указанные
 * @author niculichev
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ReplaceValueFieldValuesSourceDecorator implements FieldValuesSource
{
	// оригинальный источник данных
	private FieldValuesSource valuesSource;
	// соответствие имя поля и значений для замены
	private Map<String, Map<String, String>> replaceValues;

	/**
	 * Конструктор
	 * @param valuesSource оригинальный источник данных
	 * @param replaceValues мап значений ввида: имя значения - мап значений для замены(оригинальное значение - значение для замены)
	 */
	public ReplaceValueFieldValuesSourceDecorator(FieldValuesSource valuesSource, Map<String, Map<String, String>> replaceValues)
	{
		this.valuesSource = valuesSource;
		this.replaceValues = replaceValues;
	}

	public String getValue(String name)
	{
		String origValue = valuesSource.getValue(name);
		// нечем заменять
		if(MapUtils.isEmpty(replaceValues))
			return origValue;

		// нечем заменять
		Map<String, String> values = replaceValues.get(name);
		if(MapUtils.isEmpty(values))
			return origValue;

		// если нет значения для замены
		String replceValue = values.get(origValue);
		if(replceValue == null)
			return origValue;

		return replceValue;
	}

	public Map<String, String> getAllValues()
	{
		Map<String, String> origValues = valuesSource.getAllValues();
		if(MapUtils.isEmpty(replaceValues))
			return origValues;

		Map<String, String> resultValues = new HashMap<String, String>(origValues);
		for(String key : replaceValues.keySet())
		{
			Map<String, String> replaceValueMap = replaceValues.get(key);
			// если для этого поля нет значений для замены
			if(MapUtils.isEmpty(replaceValueMap))
				continue;

			String origValue = valuesSource.getValue(key);
			String replaceValue = replaceValueMap.get(origValue);
			// нечем заменить
			if(replaceValue == null)
				continue;

			// иначе заменяем
			resultValues.put(key, replaceValue);
		}

		return resultValues;
	}

	public boolean isChanged(String name)
	{
		return valuesSource.isChanged(name);
	}

	public boolean isEmpty()
	{
		return valuesSource.isEmpty();
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}

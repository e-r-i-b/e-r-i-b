package com.rssl.common.forms.processing;

import org.apache.commons.collections.MapUtils;

import java.util.Map;
import java.util.HashMap;

/**
 * ������� ������, ���������� ������������ �������� �� ���������
 * @author niculichev
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ReplaceValueFieldValuesSourceDecorator implements FieldValuesSource
{
	// ������������ �������� ������
	private FieldValuesSource valuesSource;
	// ������������ ��� ���� � �������� ��� ������
	private Map<String, Map<String, String>> replaceValues;

	/**
	 * �����������
	 * @param valuesSource ������������ �������� ������
	 * @param replaceValues ��� �������� �����: ��� �������� - ��� �������� ��� ������(������������ �������� - �������� ��� ������)
	 */
	public ReplaceValueFieldValuesSourceDecorator(FieldValuesSource valuesSource, Map<String, Map<String, String>> replaceValues)
	{
		this.valuesSource = valuesSource;
		this.replaceValues = replaceValues;
	}

	public String getValue(String name)
	{
		String origValue = valuesSource.getValue(name);
		// ����� ��������
		if(MapUtils.isEmpty(replaceValues))
			return origValue;

		// ����� ��������
		Map<String, String> values = replaceValues.get(name);
		if(MapUtils.isEmpty(values))
			return origValue;

		// ���� ��� �������� ��� ������
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
			// ���� ��� ����� ���� ��� �������� ��� ������
			if(MapUtils.isEmpty(replaceValueMap))
				continue;

			String origValue = valuesSource.getValue(key);
			String replaceValue = replaceValueMap.get(origValue);
			// ����� ��������
			if(replaceValue == null)
				continue;

			// ����� ��������
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

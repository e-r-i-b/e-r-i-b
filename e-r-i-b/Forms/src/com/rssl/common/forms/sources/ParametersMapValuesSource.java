package com.rssl.common.forms.sources;

import com.rssl.common.forms.processing.FieldValuesSource;

import java.util.Map;
import java.util.HashMap;

/**
 * @author mihaylov
 * @ created 16.05.2010
 * @ $Author$
 * @ $Revision$
 */
//ParametersMapValuesSource отличается от MapValuesSource, тем что в качестве значений в MAP-е используются массывы
public class ParametersMapValuesSource implements FieldValuesSource
{
	private Map map;

	/**
	 * @param map содержит map параметров из реквеста.
	 */
	public ParametersMapValuesSource(Map map)
	{
		this.map = map;
	}

	public String getValue(String name)
	{
		String[] values = (String[])map.get(name);
		if (values == null || values.length == 0){
			return null;
		}
		return values[values.length-1];
	}

	public Map<String, String> getAllValues()
	{
		Map<String, String> values = new HashMap<String, String>(map.size());
		for (Object key : map.keySet()) {
			String[] v = (String[])map.get(key);
			if (v!=null && v.length>0)
				values.put((String) key, v[v.length-1]);
		}
		return values;
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}

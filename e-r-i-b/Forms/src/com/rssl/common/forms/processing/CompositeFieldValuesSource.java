package com.rssl.common.forms.processing;

import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Krenev
 * @ created 02.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class CompositeFieldValuesSource implements FieldValuesSource
{
	private final List<FieldValuesSource> sourses;

	public CompositeFieldValuesSource(FieldValuesSource ... sourses)
	{
		this.sourses = new LinkedList<FieldValuesSource>(Arrays.asList(sourses));
	}

	public void add(FieldValuesSource source)
	{
		this.sourses.add(source);
	}

	public String getValue(String name)
	{
		for (FieldValuesSource source:sourses){
			String value = source.getValue(name);
			if (!StringHelper.isEmpty(value))
				return value;
		}
		return null;
	}

	public Map<String, String> getAllValues()
	{
		Map<String, String> values = new HashMap<String, String>();
		for (FieldValuesSource source : sourses)
		{
			for (Map.Entry<String, String> entry : source.getAllValues().entrySet())
			{
				if (StringHelper.isEmpty(values.get(entry.getKey())))
				{
					//если предшедствующий источник(он имеет более высокий приоритет)
					//не добавил значение, добавляем текущее.
					values.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return values;
	}

	public boolean isChanged(String name)
	{
		for (FieldValuesSource source:sourses){
			String value = source.getValue(name);
			if (value != null){
				return source.isChanged(name);
			}
		}
		return false;
	}

	public boolean isEmpty()
	{
		for (FieldValuesSource source : sourses)
		{
			if (!source.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	public boolean isMasked(String name)
	{
		for (FieldValuesSource source : sourses)
		{
			String value = source.getValue(name);
			// если значение есть, то берется из текущего соурса,
			// значит у него и проверяем на маскированность
			if (StringHelper.isNotEmpty(value))
				return source.isMasked(name);
		}

		return false;
	}
}

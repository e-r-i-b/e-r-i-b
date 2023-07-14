package com.rssl.common.forms.sources;

import com.rssl.common.forms.processing.FieldValuesSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * »звлекает значени€ полей из Map
 * @author Evgrafov
 * @ created 15.12.2005
 * @ $Author: balovtsev $
 * @ $Revision: 61649 $
 */
public class MapValuesSource implements FieldValuesSource
{
	private Map map;

	/**
	 * @param map содержит имена полей в качестве key и значение полей в качестве value
	 * @noinspection AssignmentToCollectionOrArrayFieldFromParameter
	 **/
	public MapValuesSource(Map map)
	{
		this.map = map;
	}

	public String getValue(String name)
	{
		Object value = map.get(name);
		if(value == null)
			return null;
		if(value instanceof String)
			return (String) value;

		if (value instanceof Date)
		{
			DateFormat format = new SimpleDateFormat();
			return format.format((Date) value);
		}

		if (value instanceof Calendar)
		{
			DateFormat format = new SimpleDateFormat();
			return format.format(((Calendar) value).getTime());
		}

		return value.toString();
	}

	public Map<String, String> getAllValues()
	{
		return Collections.unmodifiableMap(map);
	}

	/**
	 * @return ¬озвращает набор в том виде в каком еЄ инициализировали
	 */
	public Map getValuesAsIs()
	{
		return Collections.unmodifiableMap(map);
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

package com.rssl.phizic.operations.payment;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author Erkin
 * @ created 09.08.2010
 * @ $Author$
 * @ $Revision$
 */
class FieldValueReader
{
	private final Map<String, String> source;

	FieldValueReader(Map<String, String> source)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.source = source;
	}

	String getString(String fieldKey)
	{
		if (StringHelper.isEmpty(fieldKey))
			throw new IllegalArgumentException("Argument 'fieldKey' cannot be empty");

		Object value = source.get(fieldKey);
		if (value == null)
			return null;

		if (value instanceof String)
			return (String) value;

		throw new RuntimeException("Cannot read field '" + fieldKey + "' as string");
	}

	Long getLong(String fieldKey)
	{
		if (StringHelper.isEmpty(fieldKey))
			throw new IllegalArgumentException("Argument 'fieldKey' cannot be empty");

		Object value = source.get(fieldKey);
		if (value == null)
			return null;

		if (value instanceof Long)
			return (Long) value;

		if (value instanceof String) {
			String svalue = (String) value;
			if (StringUtils.isBlank(svalue))
				return null;
			// TODO: что делать с NumberFormatException?
			return Long.parseLong((String) value);
		}

		throw new RuntimeException("Cannot read field '" + fieldKey + "' as long");
	}

	Long getId(String fieldKey)
	{
		Long id = getLong(fieldKey);
		if (id != null && id > 0)
			return id;
		else return null;
	}
}

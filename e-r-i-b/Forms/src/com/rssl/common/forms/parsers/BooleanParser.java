package com.rssl.common.forms.parsers;

import com.rssl.phizic.utils.StringHelper;

import java.util.Set;
import java.util.HashSet;

/**
 * Парсер булевых значений
 * @author Kidyaev
 * @ created 13.02.2006
 * @ $Author: erkin $
 * @ $Revision: 23762 $
 */
public class BooleanParser implements FieldValueParser
{
	private static final Set<String> trues = new HashSet<String>();

	static
	{
		trues.add("1");
		trues.add("on");
		trues.add("true");
	}

	/**
	 *
 	 * @param value значение
	 *  -
	 * @return true если lower(<value>) одно из "1", "true", "on"
	 */
	public Boolean parse(String value)
	{
		if (StringHelper.isEmpty(value))
			return Boolean.FALSE;

		if (trues.contains(value.toLowerCase()))
			return Boolean.TRUE;

		return Boolean.FALSE;
	}
}

package com.rssl.common.forms.validators;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erkin
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверяет, что значение входит в заданный диапазон
 */
public class RangeFieldValidator extends FieldValidatorBase
{
	private final Set<String> range;

	public RangeFieldValidator(String... range)
	{
		if (ArrayUtils.isEmpty(range))
			throw new IllegalArgumentException("Аргумент 'range' не может быть пустым");
		this.range = new HashSet<String>(Arrays.asList(range));
	}

	public RangeFieldValidator(Collection<String> range)
	{
		if (CollectionUtils.isEmpty(range))
			throw new IllegalArgumentException("Аргумент 'range' не может быть пустым");
		this.range = new HashSet<String>(range);
	}

	public RangeFieldValidator(Collection<String> range, String message)
	{
		this(range);
		setMessage(message);
	}

	public boolean validate(String value)
	{
		if (StringHelper.isEmpty(value))
			return true;

		return range.contains(value);
	}
}

package com.rssl.phizic.gate.templates.attributable;

import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Доп. атрибут типа Decimal
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class DecimalAttribute extends ExtendedAttributeBase
{
	protected DecimalAttribute() {};

	protected DecimalAttribute(String name, Object value)
	{
		super(name, value);
	}

	protected DecimalAttribute(Long id, String name, Object value)
	{
		super(id, name, value);
	}

	public Type getType()
	{
		return Type.DECIMAL;
	}

	public BigDecimal getValue()
	{
		String value = getStringValue();
		if (StringHelper.isEmpty(value))
		{
			return null;
		}

		try
		{
			BigDecimal temp = NumericUtil.parseBigDecimal(StringUtils.deleteWhitespace(value));
			return temp.scale() == 0 ? temp.setScale(2) : temp;
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected String format(Object value)
	{
		if (value == null || value instanceof String)
		{
			return super.format(value);
		}

		assert value.getClass().equals(BigDecimal.class);
		return value.toString();
	}
}

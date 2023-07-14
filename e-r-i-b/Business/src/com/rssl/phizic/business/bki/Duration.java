package com.rssl.phizic.business.bki;

import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Gulov
 * @ created 15.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Срок кредита
 */
public class Duration
{
	private Long value;
	private String unit;

	public Duration(Long value, String unit)
	{
		this.value = value;
		this.unit = unit;
	}

	public Long getValue()
	{
		return value;
	}

	public void setValue(Long value)
	{
		this.value = value;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	@Override public String toString()
	{
		if (NumericUtil.isEmpty(value) || StringHelper.isEmpty(unit))
			return "";
		return value + " " + unit.substring(0, 1) + ".";
	}
}

package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */
class SimpleValue extends AbstractValue
{
	private String valueTitle;
	private Object value;

	SimpleValue(Object value, String valueTitle, boolean mandatory, int commaCount)
	{
		super(mandatory, commaCount);
		this.valueTitle = valueTitle;
		this.value = value;
	}

	SimpleValue(Object value, String valueTitle)
	{
		super(true, 1);
		this.valueTitle = valueTitle;
		this.value = value;
	}

	public Object getValue()
	{
		return replaceComma(String.valueOf(value));
	}

	public boolean isEmpty()
	{
		return value == null || StringHelper.isEmpty(String.valueOf(value));
	}

	public String getMessage()
	{
		return valueTitle;
	}
}

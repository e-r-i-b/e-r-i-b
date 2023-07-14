package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class ExactAmountTemplateIntervalFilter extends TemplateIntervalFilterBase<BigDecimal>
{
	public static final String TO_FILTER_VALUE_ATTRIBUTE_NAME           = "amountTo";
	public static final String FROM_FILTER_VALUE_ATTRIBUTE_NAME         = "amountFrom";


	private BigDecimal toValue;
	private BigDecimal fromValue;

	public ExactAmountTemplateIntervalFilter(Map<String, Object> params)
	{
		toValue = (BigDecimal) params.get(TO_FILTER_VALUE_ATTRIBUTE_NAME);
		fromValue = (BigDecimal) params.get(FROM_FILTER_VALUE_ATTRIBUTE_NAME);
	}

	@Override
	protected BigDecimal getFromValue()
	{
		return fromValue;
	}

	@Override
	protected BigDecimal getToValue()
	{
		return toValue;
	}

	@Override
	protected BigDecimal getValue(TemplateDocument template)
	{
		Money money = template.getExactAmount();
		if (money == null)
		{
			return null;
		}
		return money.getDecimal();
	}
}

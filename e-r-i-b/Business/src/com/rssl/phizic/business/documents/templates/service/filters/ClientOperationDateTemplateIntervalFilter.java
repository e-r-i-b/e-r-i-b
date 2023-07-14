package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Фильтр шаблонов платежей по интервалу даты подтверждения шаблона клиентом
 *
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientOperationDateTemplateIntervalFilter extends TemplateIntervalFilterBase<Calendar>
{
	public static final String TO_FILTER_VALUE_ATTRIBUTE_NAME           = "confirmDateTo";
	public static final String FROM_FILTER_VALUE_ATTRIBUTE_NAME         = "confirmDateFrom";


	private Calendar toValue;
	private Calendar fromValue;

	public ClientOperationDateTemplateIntervalFilter(Map<String, Object> params)
	{
		toValue = DateHelper.toCalendar((Date) params.get(TO_FILTER_VALUE_ATTRIBUTE_NAME));
		fromValue = DateHelper.toCalendar((Date) params.get(FROM_FILTER_VALUE_ATTRIBUTE_NAME));
	}

	@Override
	protected Calendar getFromValue()
	{
		return fromValue;
	}

	@Override
	protected Calendar getToValue()
	{
		return toValue;
	}

	@Override
	protected Calendar getValue(TemplateDocument template)
	{
		return template.getClientOperationDate();
	}
}

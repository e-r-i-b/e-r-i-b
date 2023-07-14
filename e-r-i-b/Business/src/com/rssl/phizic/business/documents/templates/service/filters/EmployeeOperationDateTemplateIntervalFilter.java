package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Фильтр шаблонов платежей по интервалу даты подтверждения шаблона сотрудником
 *
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeOperationDateTemplateIntervalFilter extends TemplateIntervalFilterBase<Calendar>
{
	public static final String TO_FILTER_VALUE_ATTRIBUTE_NAME           = "stateChangeDateTo";
	public static final String FROM_FILTER_VALUE_ATTRIBUTE_NAME         = "stateChangeDateFrom";


	private Calendar toValue;
	private Calendar fromValue;

	public EmployeeOperationDateTemplateIntervalFilter(Map<String, Object> params)
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
		return template.getAdditionalOperationDate();
	}
}


package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Фильтр шаблонов платежей по номеру шаблона
 *
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateNumberFilter implements TemplateDocumentFilter
{
	public static final String TEMPLATE_NUMBER_FILTER_ATTRIBUTE_NAME           = "templateNumber";

	private String number;

	public TemplateNumberFilter(Map<String, Object> params)
	{
		number = (String) params.get(TEMPLATE_NUMBER_FILTER_ATTRIBUTE_NAME);
	}

	public boolean accept(TemplateDocument template)
	{
		if (StringHelper.isEmpty(number))
		{
			return true;
		}

		return number.equals(template.getDocumentNumber());
	}
}

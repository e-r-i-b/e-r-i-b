package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.common.types.documents.templates.ActivityCode;

/**
 * Фильтр проверяет готовность шаблона к проведению по нему операции
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ReadyToPaymentTemplateFilter implements TemplateDocumentFilter
{
	public boolean accept(TemplateDocument template)
	{
		String activityCode = template.getTemplateInfo().getState().getCode();
		if (!ActivityCode.ACTIVE.name().equals(activityCode))
		{
			return false;
		}

		String stateCode = template.getState().getCode();
		if (!(StateCode.TEMPLATE.name().equals(stateCode) || StateCode.WAIT_CONFIRM_TEMPLATE.name().equals(stateCode)))
		{
			return false;
		}

		return true;
	}
}

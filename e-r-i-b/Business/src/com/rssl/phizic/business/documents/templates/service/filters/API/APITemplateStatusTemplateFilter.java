package com.rssl.phizic.business.documents.templates.service.filters.API;

import com.rssl.phizic.business.documents.templates.APITemplateStatus;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.TemplateDocumentFilter;
import com.rssl.phizic.utils.StringHelper;

/**
 * Фильтр шаблонов документов по статусу в АПИ
 *
 * @author khudyakov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 */
public class APITemplateStatusTemplateFilter implements TemplateDocumentFilter
{
	private String status;
	private String[] supportedForms;

	public APITemplateStatusTemplateFilter(String status, String[] supportedForms)
	{
		this.status = status;
		this.supportedForms = supportedForms;
	}

	public boolean accept(TemplateDocument template)
	{
		if (StringHelper.isEmpty(status))
		{
			return true;
		}

		APITemplateStatus apiTemplateStatus = APITemplateStatus.valueOf(status);
		boolean supported = new APIFormTemplateFilter(supportedForms).accept(template);

		if (APITemplateStatus.available == apiTemplateStatus)
		{
			return template.getActivityInfo().isAvailablePay() && supported;
		}

		if (APITemplateStatus.notAvailable == apiTemplateStatus)
		{
			return !template.getActivityInfo().isAvailablePay() || !supported;
		}

		throw new IllegalArgumentException();
	}
}

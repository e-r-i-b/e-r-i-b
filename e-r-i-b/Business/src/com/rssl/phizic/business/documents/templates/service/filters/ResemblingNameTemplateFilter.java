package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Фильтр названий шаблонов документов, отбирает похожие наименования.
 *
 * @author khudyakov
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ResemblingNameTemplateFilter implements TemplateDocumentFilter
{
	public static final String TEMPLATE_NAME_FILTER_ATTRIBUTE_NAME           = "templateName";


	private String templateName;

	public ResemblingNameTemplateFilter(String templateName)
	{
		this.templateName = templateName;
	}

	public ResemblingNameTemplateFilter(Map<String, Object> params)
	{
		templateName = (String) params.get(TEMPLATE_NAME_FILTER_ATTRIBUTE_NAME);
	}

	public boolean accept(TemplateDocument template)
	{
		if (StringHelper.isEmpty(templateName))
		{
			return true;
		}

		return template.getTemplateInfo().getName().contains(templateName);
	}
}

package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * Фильтр по названию шаблона
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class NameTemplateFilter implements TemplateDocumentFilter
{
	private String templateName;


	public NameTemplateFilter(String templateName)
	{
		this.templateName = templateName;
	}

	public boolean accept(TemplateDocument template)
	{
		return template.getTemplateInfo().getName().equals(templateName);
	}
}

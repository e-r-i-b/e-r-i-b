package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * @author osminin
 * @ created 19.11.14
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр, исключающий напоминания из шаблонов
 */
public class NotReminderTemplateFilter extends ReminderTemplateFilter
{
	@Override
	public boolean accept(TemplateDocument template)
	{
		return !super.accept(template);
	}
}

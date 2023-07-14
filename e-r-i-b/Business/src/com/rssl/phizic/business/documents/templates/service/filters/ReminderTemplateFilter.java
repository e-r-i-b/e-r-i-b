package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.payments.template.ReminderInfo;

/**
 * @author osminin
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр напоминаний
 */
public class ReminderTemplateFilter implements TemplateDocumentFilter
{
	public boolean accept(TemplateDocument template)
	{
		ReminderInfo reminderInfo = template.getReminderInfo();
		//напоминание отличает от шаблона наличие типа напоминания
		return reminderInfo != null && reminderInfo.getType() != null;
	}
}

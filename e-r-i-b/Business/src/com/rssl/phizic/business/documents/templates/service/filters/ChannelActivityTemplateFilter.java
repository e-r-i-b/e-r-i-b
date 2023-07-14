package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.payments.template.TemplateInfo;

/**
 * Фильтр активности шаблона документа в канале
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ChannelActivityTemplateFilter implements TemplateDocumentFilter
{
	private CreationType channelType;

	public ChannelActivityTemplateFilter(CreationType channelType)
	{
		this.channelType = channelType;
	}

	public boolean accept(TemplateDocument template)
	{
		TemplateInfo info = template.getTemplateInfo();
		switch (channelType)
		{
			case internet: return info.isUseInERIB();
			case mobile: return info.isUseInMAPI();
			case sms: return info.isUseInERMB();
			case atm: return info.isUseInATM();
			default: return false;
		}
	}
}

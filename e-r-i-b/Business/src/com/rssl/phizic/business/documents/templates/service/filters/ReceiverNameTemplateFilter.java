package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Фильтр шаблонов платежей по названию получателя платежа
 *
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class ReceiverNameTemplateFilter implements TemplateDocumentFilter
{
	private String receiverName;

	public ReceiverNameTemplateFilter(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public ReceiverNameTemplateFilter(Map<String, Object> params)
	{
		receiverName = (String) params.get(Constants.RECEIVER_NAME_ATTRIBUTE_NAME);
	}

	public boolean accept(TemplateDocument template)
	{
		if (StringHelper.isEmpty(receiverName))
		{
			return true;
		}

		return template.getReceiverName().contains(receiverName);
	}
}

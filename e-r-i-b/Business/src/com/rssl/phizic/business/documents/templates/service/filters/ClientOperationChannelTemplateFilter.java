package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Фильтр шаблонов документов по каналу подтверждения клиентом
 *
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientOperationChannelTemplateFilter extends ChannelTemplateFilterBase
{
	public static final String CLIENT_OPERATION_CHANNEL_FILTER_ATTRIBUTE_NAME = "confirmChannelType";


	private CreationType channelType;

	public ClientOperationChannelTemplateFilter(Map<String, Object> params)
	{
		String s = (String) params.get(CLIENT_OPERATION_CHANNEL_FILTER_ATTRIBUTE_NAME);
		if (StringHelper.isNotEmpty(s))
		{
			channelType = CreationType.fromValue(s);
		}
	}

	@Override
	protected CreationType getChannelType()
	{
		return channelType;
	}

	@Override
	protected CreationType getValue(TemplateDocument template)
	{
		return template.getClientOperationChannel();
	}
}

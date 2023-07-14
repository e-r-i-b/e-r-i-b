package com.rssl.phizic.messaging.loaders.push;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.push.PushResourcesService;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.push.MessageBuilderType;
import com.rssl.phizic.messaging.mail.messagemaking.TemplateLoaderBase;

import java.util.regex.Pattern;

/**
 * @author basharin
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 */

public class FullMessageInformingPushLoader extends TemplateLoaderBase
{
	private static final PushResourcesService pushResourcesService = new PushResourcesService();
	private static final Pattern pattern = Pattern.compile(MessageBuilderType.FULL_MESSAGE.getTemplateRegexp());

	@Override protected String findTemplate(String key) throws IKFLMessagingException
	{
		try
		{
			return pushResourcesService.getPushFullMessage(key);
		}
		catch (BusinessException e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	@Override protected Pattern getPattern()
	{
		return pattern;
	}
}

package com.rssl.phizic.messaging.loaders.push;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.push.PushResourcesService;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.push.MessageBuilderType;
import com.rssl.phizic.messaging.mail.messagemaking.TemplateLoaderBase;

import java.util.regex.Pattern;

/**
 * @author osminin
 * @ created 09.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Лоадер атрибутов для push уведомлений
 */
public class AttributesInformingPushLoader extends TemplateLoaderBase
{
	private static final Pattern pattern = Pattern.compile(MessageBuilderType.ATTRIBUTES.getTemplateRegexp());
	private static PushResourcesService pushResourcesService = new PushResourcesService();

	@Override
	protected String findTemplate(String key) throws IKFLMessagingException
	{
		try
		{
			return pushResourcesService.getPushAttributes(key);
		}
		catch (BusinessException e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	@Override
	protected Pattern getPattern()
	{
		return pattern;
	}
}

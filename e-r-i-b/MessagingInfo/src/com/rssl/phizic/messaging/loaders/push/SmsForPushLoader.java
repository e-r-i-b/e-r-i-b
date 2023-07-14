package com.rssl.phizic.messaging.loaders.push;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.SMSResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.push.MessageBuilderType;
import com.rssl.phizic.messaging.mail.messagemaking.TemplateLoaderBase;

import java.util.regex.Pattern;

/**
 * базовый загрузчик текста смс сообщений для push-сообщений
 * @author basharin
 * @ created 20.11.13
 * @ $Author$
 * @ $Revision$
 */

abstract class SmsForPushLoader extends TemplateLoaderBase
{
	private static Pattern pattern = Pattern.compile(MessageBuilderType.SMS_MESSAGE.getTemplateRegexp());
	private SMSResourcesService smsResourcesService = new SMSResourcesService();

	@Override protected String findTemplate(String key) throws IKFLMessagingException
	{
		SMSResource smsResource = null;
		try
		{
			smsResource = smsResourcesService.findResourcesByKey((Class<? extends SMSResource>) getResourcesType(), key, getChannelType());
		}
		catch (BusinessException e)
		{
			throw new IKFLMessagingException(e);
		}
		if (smsResource == null)
			return null;
		return smsResource.getText();
	}

	@Override protected Pattern getPattern()
	{
		return pattern;
	}

	protected abstract Class<? extends MessageResource> getResourcesType();
}
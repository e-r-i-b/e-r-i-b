package com.rssl.phizic.messaging.loaders.push;

import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.SMSConfirmationResource;

/**
 * загрузчик текста смс сообщений для push-сообщений с паролем
 * @author basharin
 * @ created 20.11.13
 * @ $Author$
 * @ $Revision$
 */

public class PasswordSmsForPushLoader extends SmsForPushLoader
{
	@Override protected Class<? extends MessageResource> getResourcesType()
	{
		return SMSConfirmationResource.class;
	}
}

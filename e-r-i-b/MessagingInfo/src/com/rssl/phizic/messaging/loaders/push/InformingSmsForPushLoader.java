package com.rssl.phizic.messaging.loaders.push;

import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.SMSInformingResource;

/**
 * загрузчик текста смс сообщений для информационных push-сообщений
 * @author basharin
 * @ created 20.11.13
 * @ $Author$
 * @ $Revision$
 */

public class InformingSmsForPushLoader extends SmsForPushLoader
{
	@Override protected Class<? extends MessageResource> getResourcesType()
	{
		return SMSInformingResource.class;
	}
}

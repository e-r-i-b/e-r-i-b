package com.rssl.phizic.messaging.loaders.sms;

import com.rssl.phizic.business.sms.SMSInformingResource;
import com.rssl.phizic.business.sms.SMSResource;

/**
 * @author Balovtsev
 * @since 01.04.13 16:09
 */
public class InformingMessageLoader extends MessageLoader
{
	@Override
	protected Class<? extends SMSResource> getSmsResourcesType()
	{
		return SMSInformingResource.class;
	}
}

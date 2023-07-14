package com.rssl.phizic.messaging.loaders.sms;


import com.rssl.phizic.business.sms.SMSConfirmationResource;
import com.rssl.phizic.business.sms.SMSResource;

/**
 * @author emakarov
 * @ created 28.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class PasswordMessageLoader extends MessageLoader
{
	@Override
	protected Class<? extends SMSResource> getSmsResourcesType()
	{
		return SMSConfirmationResource.class;
	}
}

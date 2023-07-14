package com.rssl.phizic.messaging.loaders.sms;


import com.rssl.phizic.business.sms.SMSRefusingResource;
import com.rssl.phizic.business.sms.SMSResource;

/**
 *
 * @author komarov
 * @ created 26.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class RefusingMessageLoader extends MessageLoader
{
	@Override
	protected Class<? extends SMSResource> getSmsResourcesType()
	{
		return SMSRefusingResource.class;
	}
}

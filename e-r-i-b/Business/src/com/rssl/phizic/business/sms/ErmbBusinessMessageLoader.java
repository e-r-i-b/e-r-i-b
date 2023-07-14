package com.rssl.phizic.business.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.messaging.mail.messagemaking.BaseLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Загрузчик информационных смс для ЕРМБ
 * @author Puzikov
 * @ created 14.03.14
 * @ $Author$
 * @ $Revision$
 */

class ErmbBusinessMessageLoader extends BaseLoader
{
	private final SMSResourcesService smsResourcesService = new SMSResourcesService();

	private SMSResource smsResource;

	/**
	 * @return смс ресурс (информационный)
	 */
	public SMSResource getSmsResource()
	{
		return smsResource;
	}

	public Object findTemplateSource(String key) throws IOException
	{
		try
		{
			smsResource = smsResourcesService.findResourcesByKey(SMSInformingResource.class, key, ChannelType.ERMB_SMS);
			return smsResource;
		}
		catch (BusinessException e)
		{
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new IOException(e.getMessage());
		}
	}

	public long getLastModified(Object templateSource)
	{
		return -1;
	}

	public Reader getReader(Object smsResources, String encoding) throws IOException
	{
		return new StringReader(((SMSResource) smsResources).getText());
	}

	public void closeTemplateSource(Object templateSource) throws IOException
	{
	}
}

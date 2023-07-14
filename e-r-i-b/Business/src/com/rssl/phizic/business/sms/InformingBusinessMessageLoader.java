package com.rssl.phizic.business.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.messaging.mail.messagemaking.BaseLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author Nady
 * @ created 25.05.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Загрузчик информационных смс для Интернет-клиента
 */
public class InformingBusinessMessageLoader extends BaseLoader
{
	private final SMSResourcesService smsResourcesService = new SMSResourcesService();

	private SMSResource smsResource;

	public Object findTemplateSource(String key) throws IOException
	{
		try
		{
			smsResource = smsResourcesService.findResourcesByKey(SMSInformingResource.class, key, ChannelType.INTERNET_CLIENT);
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

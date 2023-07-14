package com.rssl.phizic.messaging.loaders.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.sms.SMSResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.messaging.mail.messagemaking.BaseLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 *
 * @author komarov
 * @ created 26.02.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class MessageLoader extends BaseLoader
{
	private static final String DELIMETER = "\\^";
	private SMSResourcesService smsResourcesService = new SMSResourcesService();
	private SMSResource smsResource                 = null;// смс-сообщение данного лоадера


	public SMSResource getSmsResource()
	{
		return smsResource;
	}

	public long getLastModified(Object templateSource)
	{
		return -1;
	}

	public Reader getReader(Object smsResources, String encoding) throws IOException
	{
		return new StringReader(((SMSResource) smsResources).getText());
	}

	public Object findTemplateSource(String key) throws IOException
	{
		try
		{
			if (MultiLocaleContext.isDefaultLocale())
			{
				this.smsResource = smsResourcesService.findResourcesByKey(getSmsResourcesType(), key, getChannelType());
			}
			else
			{
				String[] params = key.split(DELIMETER);
				String localeId = params[0];
				String localedKey = params[1];
				this.smsResource = smsResourcesService.findResourcesByLocaleAndKey(getSmsResourcesType(), localedKey, localeId, getChannelType());
			}
			return smsResource;
		}
		catch (BusinessException e)
		{
			throw new IOException(e.getMessage());
		}
	}


	public void closeTemplateSource(Object templateSource) throws IOException {}

	protected abstract Class<? extends SMSResource> getSmsResourcesType();
}

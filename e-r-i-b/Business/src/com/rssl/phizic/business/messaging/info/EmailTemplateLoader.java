package com.rssl.phizic.business.messaging.info;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.email.EmailResourceService;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.messaging.mail.messagemaking.TemplateLoaderBase;
import com.rssl.phizic.messaging.mail.messagemaking.email.HtmlEmailMessageBuilder;
import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Evgrafov
 * @ created 05.07.2006
 * @ $Author: gladishev $
 * @ $Revision: 55614 $
 */

public class EmailTemplateLoader implements TemplateLoader
{
	private static final Set<String> ALLOVED_KEYS;

	static
	{
		ALLOVED_KEYS = new HashSet<String>(3);
		ALLOVED_KEYS.add(HtmlEmailMessageBuilder.TITLE_TEMPLATE_KEY);
		ALLOVED_KEYS.add(MailFormat.HTML.getKey());
		ALLOVED_KEYS.add(MailFormat.PLAIN_TEXT.getKey());
	}

	private EmailResourceService distributionService = new EmailResourceService();

	public Object findTemplateSource(String name) throws IOException
	{
		String[] list = name.split("\\" + TemplateLoaderBase.SEPARATOR);
		try
		{
			if (ALLOVED_KEYS.contains(list[1]))
				return distributionService.getEmailTemplate(list[0], list[1]);
		}
		catch (BusinessException e)
		{
			throw new IOException(e.getMessage());
		}
		return null;
	}

	public long getLastModified(Object templateSource)
	{
		return -1;
	}

	public Reader getReader(Object templateSource, String encoding) throws IOException
	{
		return new StringReader((String) templateSource);
	}

	public void closeTemplateSource(Object templateSource) throws IOException
	{
	}
}
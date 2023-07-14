package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;

/**
 * заготовка загрузчика шаблонов push-сообщений
 * @author basharin
 * @ created 10.10.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class TemplateLoaderBase extends BaseLoader
{
	public static final String SEPARATOR = "|";

	public long getLastModified(Object templateSource)
	{
		return -1;
	}

	public Reader getReader(Object text, String encoding) throws IOException
	{
		return new StringReader((String) text);
	}

	public String findTemplateSource(String string) throws IOException
	{
        if (getPattern().matcher(string).matches())
		{
			String[] list = string.split("\\" + SEPARATOR);
			try
			{
				return findTemplate(list[0]);
			}
			catch (IKFLMessagingException e)
			{
				throw new IOException(e.getMessage());
			}
		}
		return null;
	}

	protected abstract String findTemplate(String key) throws IKFLMessagingException;

	protected abstract Pattern getPattern();

	public void closeTemplateSource(Object templateSource) throws IOException {}
}

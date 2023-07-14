package com.rssl.phizic.business.dictionaries.receivers;

import com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor;
import com.rssl.phizic.business.BusinessException;
import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Загружает строки формирования основания платежа движка freemarker
 * @author Evgrafov
 * @ created 02.05.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4093 $
 */

public class PaymentRecieversGroundTemplateLoader implements TemplateLoader
{
	private PaymentReceiversDictionary dictionary;

	/**
	 * ctor
	 */
	public PaymentRecieversGroundTemplateLoader()
	{
		try
		{
			this.dictionary = new PaymentReceiversDictionary();
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public Object findTemplateSource(String name) throws IOException
	{
		return name;
	}

	public long getLastModified(Object templateSource)
	{
		return -1;
	}

	public Reader getReader(Object templateSource, String encoding) throws IOException
	{
		String[] strings = ((String) templateSource).split("#");

		ReceiverDescriptor descriptor = dictionary.getReceiverDescriptor(strings[0], strings[1]);
		return new StringReader(descriptor.getGroundExpression());
	}

	public void closeTemplateSource(Object templateSource) throws IOException
	{
		// do nothing
	}
}
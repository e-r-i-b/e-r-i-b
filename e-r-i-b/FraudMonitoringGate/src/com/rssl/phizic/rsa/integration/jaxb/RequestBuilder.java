package com.rssl.phizic.rsa.integration.jaxb;

import com.rssl.phizic.rsa.integration.jaxb.generated.ObjectFactory;
import com.rssl.phizic.rsa.integration.jaxb.generated.Request;
import com.rssl.phizic.utils.StringHelper;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * @author khudyakov
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public class RequestBuilder
{
	private static final RequestBuilder INSTANCE = new RequestBuilder();

	private static JAXBContext context;
	private String request;

	private RequestBuilder()
	{
		try
		{
			context = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
		}
		catch (JAXBException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return инстанс
	 */
	public static RequestBuilder getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Добавить Request
	 * @param request request
	 * @return this
	 */
	public RequestBuilder append(String request)
	{
		this.request = request;
		return this;
	}

	/**
	 * Привести Request_Type в строку;
	 * @return запрос
	 */
	public Request build() throws JAXBException
	{
		if (StringHelper.isEmpty(request))
		{
			return null;
		}

		return (Request) context.createUnmarshaller().unmarshal(new StringReader(request));
	}
}

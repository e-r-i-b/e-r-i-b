package com.rssl.phizic.monitoring.fraud.messages.jaxb;

import com.rssl.phizic.monitoring.fraud.messages.jaxb.generated.ObjectFactory;
import com.rssl.phizic.monitoring.fraud.messages.jaxb.generated.Request;
import com.rssl.phizic.utils.StringHelper;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * ������ ������ �� ��
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public class RequestBuilder
{
	private static final RequestBuilder INSTANCE = new RequestBuilder();

	private static JAXBContext context;
	private String message;

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
	 * @return �������
	 */
	public static RequestBuilder getInstance()
	{
		return INSTANCE;
	}

	/**
	 * �������� ��������
	 * @param message ���������
	 * @return RequestBuilder
	 */
	public RequestBuilder append(String message)
	{
		this.message = message;
		return this;
	}

	/**
	 * �������� ������ � �������;
	 * @return ������
	 */
	public Request build() throws JAXBException
	{
		if (StringHelper.isEmpty(message))
		{
			return null;
		}

		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (Request) unmarshaller.unmarshal(new StringReader(message));
	}
}

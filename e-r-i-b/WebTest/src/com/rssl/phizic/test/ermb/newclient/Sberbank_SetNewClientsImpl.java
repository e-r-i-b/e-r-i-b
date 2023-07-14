package com.rssl.phizic.test.ermb.newclient;

import com.rssl.phizic.business.ermb.newclient.message.generated.Request;
import com.rssl.phizic.business.ermb.newclient.message.generated.Response;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;

import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;

/**
 * @author Gulov
 * @ created 19.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Веб-сервис по приему извещений о новых клиентах ЕРМБ
 */
public class Sberbank_SetNewClientsImpl implements com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap
{
	public java.lang.String XMLMessage(java.lang.String request) throws java.rmi.RemoteException
	{
		Request message = parseRequest(request);
		Response response = buildResponse(message);
		return convertResponse(response);
	}

	private String convertResponse(Response response) throws RemoteException
	{
		try
		{
			return JAXBUtils.marshalBean(response);
		}
		catch (JAXBException e)
		{
			throw new RemoteException("Ошибка создания выходного сообщения в веб-сервисе по приему извещений о новых клиентах ЕРМБ", e);
		}
	}

	private Request parseRequest(String request) throws RemoteException
	{
		try
		{
			return JAXBUtils.unmarshalBean(Request.class, request);
		}
		catch (JAXBException e)
		{
			throw new RemoteException("Ошибка разбора входящего запроса:\n " + request, e);
		}
	}

	private Response buildResponse(Request input)
	{
		ResponseBuilder builder = new ResponseBuilder(input);
		return builder.build();
	}
}

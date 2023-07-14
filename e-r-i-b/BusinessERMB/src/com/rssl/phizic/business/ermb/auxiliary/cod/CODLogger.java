package com.rssl.phizic.business.ermb.auxiliary.cod;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.axis.Message;

import javax.xml.soap.SOAPBody;

/**
 * Логирование обмена ериб с цод
 * @author Puzikov
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class CODLogger extends AbstractAxisLogger
{
	public CODLogger()
	{
		super(System.ermb_cod);
	}

	@Override
	public String getRequestMessageType(SOAPBody body) throws LoggingException
	{
		try
		{
			return XmlHelper.parse(body.getFirstChild().getFirstChild().getFirstChild().getNodeValue()).getFirstChild().getNodeName();
		}
		catch (Exception e)
		{
			throw new LoggingException("Ошибка получения типа сообщения", e);
		}
	}

	@Override
	protected String getMessageId(SOAPBody body) throws LoggingException
	{
		try
		{
			return XmlHelper.getSimpleElementValue(XmlHelper.parse(body.getFirstChild().getFirstChild().getFirstChild().getNodeValue()).getDocumentElement(), "RqUID");
		}
		catch (Exception e)
		{
			throw new LoggingException("Ошибка получения id сообщения", e);
		}
	}

	@Override
	protected String getMessageRequest(Message request) throws LoggingException
	{
		SOAPBody responseBody = AxisLoggerHelper.getBody(request);
		return responseBody.getFirstChild().getFirstChild().getFirstChild().getNodeValue();
	}

	@Override
	protected String getMessageResponse(Message response) throws LoggingException
	{
		SOAPBody responseBody = AxisLoggerHelper.getBody(response);
		return responseBody.getFirstChild().getFirstChild().getFirstChild().getNodeValue();
	}
}

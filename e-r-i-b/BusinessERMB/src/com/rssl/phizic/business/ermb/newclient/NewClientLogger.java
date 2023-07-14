package com.rssl.phizic.business.ermb.newclient;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LoggingException;

import javax.xml.soap.SOAPBody;

/**
 * @author Gulov
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class NewClientLogger extends AbstractAxisLogger
{
	public NewClientLogger()
	{
		super(com.rssl.phizic.logging.messaging.System.ERMB_OSS);
	}

	protected String getRequestMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageShopType(body);
	}

	protected String getResponseMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageShopType(body);
	}

	@Override
	protected String getMessageId(SOAPBody body) throws LoggingException
	{
		return "fake-id";
	}
}

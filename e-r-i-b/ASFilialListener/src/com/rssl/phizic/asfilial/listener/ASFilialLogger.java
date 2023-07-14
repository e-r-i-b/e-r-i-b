package com.rssl.phizic.asfilial.listener;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LoggingException;

import javax.xml.soap.SOAPBody;

/**
 * User: moshenko
 * Date: 19.12.2012
 * Time: 11:36:33
 */
public class ASFilialLogger extends AbstractAxisLogger
{
	public ASFilialLogger()
	{
		super(com.rssl.phizic.logging.messaging.System.asfilial);
	}

	protected String getRequestMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageShopType(body);
	}

	protected String getResponseMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageShopType(body);
	}
}

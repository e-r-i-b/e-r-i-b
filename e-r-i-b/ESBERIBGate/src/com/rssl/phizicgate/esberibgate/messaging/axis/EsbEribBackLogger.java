package com.rssl.phizicgate.esberibgate.messaging.axis;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.LoggingException;

import javax.xml.soap.SOAPBody;

/**
 * @author osminin
 * @ created 15.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * логирование сообщений обратного сервиса
 */
public class EsbEribBackLogger extends AbstractAxisLogger
{
	/**
	 * ctor
	 */
	public EsbEribBackLogger()
	{
		super(System.esberib);
	}

	protected String getRequestMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageType(body);
	}

	protected String getResponseMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageType(body);
	}
}

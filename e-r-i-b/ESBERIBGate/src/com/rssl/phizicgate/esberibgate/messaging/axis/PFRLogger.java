package com.rssl.phizicgate.esberibgate.messaging.axis;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.LoggingException;

import javax.xml.soap.SOAPBody;

/**
 * @author gulov
 * @ created 21.02.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * логирование сообщений пенсионного фонда
 */
public class PFRLogger extends AbstractAxisLogger
{
	public PFRLogger()
	{
		super(System.pfr);
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

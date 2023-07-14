package com.rssl.phizicgate.esberibgate.messaging.axis;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.LoggingException;

import javax.xml.soap.SOAPBody;

/**
 * @author gulov
 * @ created 28.04.2011
 * @ $Authors$
 * @ $Revision$
 */
public class MDMLogger extends AbstractAxisLogger
{
	public MDMLogger()
	{
		super(System.mdm);
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

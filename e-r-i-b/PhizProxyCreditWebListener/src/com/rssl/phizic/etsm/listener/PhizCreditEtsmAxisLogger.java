package com.rssl.phizic.etsm.listener;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LoggingException;

import javax.xml.soap.SOAPBody;

/**
 * @author Moshenko
 * @ created 07.08.15
 * @ $Author$
 * @ $Revision$
 */
public class PhizCreditEtsmAxisLogger  extends AbstractAxisLogger
{
	public PhizCreditEtsmAxisLogger()
	{
		super(com.rssl.phizic.logging.messaging.System.eribCreditListener);
	}

	protected String getRequestMessageType(SOAPBody body
	) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageShopType(body);
	}

	protected String getResponseMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageShopType(body);
	}
}

package com.rssl.phizic.gate.depomobilebank.ws;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankConfig;
import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LoggingException;

import javax.xml.soap.SOAPBody;

/**
 * User: Moshenko
 * Date: 28.10.13
 * Time: 17:39
 */
public class MBVClientAxisLogger extends AbstractAxisLogger
{
	public MBVClientAxisLogger()
	{
		super(com.rssl.phizic.logging.messaging.System.mbv);
	}

	protected String getRequestMessageType(SOAPBody body) throws LoggingException
	{
		return  AxisLoggerHelper.resolveMessageType(body);
	}

	protected String getResponseMessageType(SOAPBody body) throws LoggingException
	{
		return  AxisLoggerHelper.resolveMessageType(body);
	}

	@Override
	protected boolean use()
	{
		return ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvLoggerUse();
	}
}

package com.rssl.phizicgate.mfm;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.logging.messaging.System;

import javax.xml.soap.SOAPBody;

/**
 * Логгер для MFM
 */
public class MFMLogger extends AbstractAxisLogger
{
	public MFMLogger()
	{
		super(System.mfm);
	}

	@Override
	protected String getRequestMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageShopType(body);
	}

	@Override
	protected String getResponseMessageType(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageShopType(body);
	}

	@Override
	protected boolean use()
	{
		//использовать ли логгирование, берем значение из проперти
		return ConfigFactory.getConfig(PhizGateMFMConfig.class).isLogging();
	}
}

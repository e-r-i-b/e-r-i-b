package com.rssl.phizic.logging;

import com.rssl.phizic.logging.messaging.System;

import javax.xml.soap.SOAPBody;

/**
 * @author gulov
 * @ created 12.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Логирование сообщений ОЗОН и УЭК
 */
public class ShopLogger extends AbstractAxisLogger
{
	/**
	 * ctor
	 */
	public ShopLogger()
	{
		super(System.shop);
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

package com.rssl.phizic.business.ermb.auxiliary.smslog;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.utils.xml.XmlHelper;

import javax.xml.soap.SOAPBody;

/**
 * @author Gulov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Логирование сообщений журнала смс от СОС
 */
public class SmsLogLogger extends AbstractAxisLogger
{
	private static final ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);

	public SmsLogLogger()
	{
		super(com.rssl.phizic.logging.messaging.System.ERMB_SOS);
	}

	protected boolean use()
	{
		return config.isSmsLogLoggerUse();
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
		return XmlHelper.getSimpleElementValue(body, "rqUID");
	}
}

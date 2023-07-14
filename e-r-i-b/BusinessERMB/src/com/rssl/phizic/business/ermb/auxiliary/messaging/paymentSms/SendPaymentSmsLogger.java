package com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.logging.messaging.*;
import com.rssl.phizic.logging.messaging.System;
import javax.xml.soap.SOAPBody;

/**
 * Логирование оповещений СОС об успешном исполнении платежа
 * @author Rtischeva
 * @created 28.08.13
 * @ $Author$
 * @ $Revision$
 */
public class SendPaymentSmsLogger extends AbstractAxisLogger
{
	public SendPaymentSmsLogger()
	{
		super(System.ERMB_SOS);
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
}

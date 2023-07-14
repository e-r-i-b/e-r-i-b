package com.rssl.phizicgate.wsgate.services.template.logging;

import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.messaging.handlers.log.JAXRPCLogHandler;

/**
 * Хендлер логирования сообщений при обмене ЕРИБ и ЕСУШ
 *
 * @author khudyakov
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 */
public class USCTJAXRPCLogHandler extends JAXRPCLogHandler
{
	@Override
	protected System getSystemId()
	{
		return System.USCT;
	}

	@Override
	protected boolean isNeedLog()
	{
		return ConfigFactory.getConfig(ConfigImpl.class).isMessageLoggingEnabled();
	}
}

package com.rssl.phizic.logging.messaging.handlers.log;

import com.rssl.phizic.logging.messaging.System;

/**
 * * Хендлер логирования сообщений при обмене ЕРИБ и ЦСА ЦА
 *
 * @author khudyakov
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 */
public class CSAJAXRPCLogHandler extends JAXRPCLogHandler
{
	@Override
	protected System getSystemId()
	{
		return System.csa;
	}
}

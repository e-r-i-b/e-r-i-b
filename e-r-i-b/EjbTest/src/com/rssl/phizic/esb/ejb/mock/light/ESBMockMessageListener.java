package com.rssl.phizic.esb.ejb.mock.light;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.ESBMockEjbMessageParser;
import com.rssl.phizic.esb.ejb.mock.ESBMockMessageListenerBase;
import com.rssl.phizic.esb.ejb.mock.ESBMockMessageParser;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * слушатель входящей очереди сегмента "легкой" шины
 */

public class ESBMockMessageListener extends ESBMockMessageListenerBase
{
	private static final MessageCoordinator messageCoordinator = new ESBMockMessageCoordinator();
	private static final ESBMockMessageParser<ESBMessage> messageParser = new ESBMockEjbMessageParser(ESBSegment.light);

	@Override
	protected MessageCoordinator getMessageCoordinator()
	{
		return messageCoordinator;
	}

	@Override
	protected ESBMockMessageParser<ESBMessage> getParser()
	{
		return messageParser;
	}
}

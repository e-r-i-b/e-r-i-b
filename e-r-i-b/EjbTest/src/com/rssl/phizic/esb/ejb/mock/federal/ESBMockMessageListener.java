package com.rssl.phizic.esb.ejb.mock.federal;

import com.rssl.phizic.esb.ejb.mock.*;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;

/**
 @author: Erkin
 @ created: 23.01.2013
 @ $Author$
 @ $Revision$
 */

/**
 * Листенер в EjbTest
 * Слушает любые сообщения
 */
public class ESBMockMessageListener extends ESBMockMessageListenerBase
{
	private static final MessageCoordinator messageCoordinator = new ESBMockMessageCoordinator();
	private static final ESBMockMessageParser<ESBMessage> messageParser = new ESBMockEjbMessageParser(ESBSegment.federal);

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

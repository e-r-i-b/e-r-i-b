package com.rssl.phizic;

import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizic.messaging.XmlMessageParser;

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
public class TestMessageListener extends EjbListenerBase
{
	private final MessageCoordinator messageCoordinator = new TestMessageCoordinator();

	private final XmlMessageParser messageParser = new TestEjbMessageParser();

	@Override
	protected XmlMessageParser getParser()
	{
		return messageParser;
	}

	@Override
	protected MessageCoordinator getMessageCoordinator()
	{
		return messageCoordinator;
	}
}

package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.messaging.MessageCoordinator;

/**
 * @author Rtischeva
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbEjbListener extends EjbListenerBase
{
	private final ErmbEjbMessageParser ermbMessageParser = new ErmbEjbMessageParser();

	private final ErmbMessageCoordinator ermbModuleCoordinator = new ErmbMessageCoordinator();

	@Override
	protected XmlMessageParser getParser()
	{
		return ermbMessageParser;
	}

	@Override
	protected MessageCoordinator getMessageCoordinator()
	{
		return ermbModuleCoordinator;
	}
}

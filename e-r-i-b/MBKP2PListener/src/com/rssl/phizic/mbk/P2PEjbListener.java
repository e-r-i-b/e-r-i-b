package com.rssl.phizic.mbk;

import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizic.messaging.XmlMessageParser;

/**
 * @author Erkin
 * @ created 24.10.2014
 * @ $Author$
 * @ $Revision$
 */
/**
 * Слушатель P2P-запросов от МБК.
 */
public class P2PEjbListener extends EjbListenerBase
{
	private final MBKP2PMessageParser ermbMessageParser = new MBKP2PMessageParser();

	private final MBKP2PMessageCoordinator ermbModuleCoordinator = new MBKP2PMessageCoordinator();

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

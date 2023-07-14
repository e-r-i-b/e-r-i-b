package com.rssl.phizic.credit;

import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizic.messaging.XmlMessageParser;

import javax.ejb.EJBException;

/**
 @author: Erkin
 @ created: 21.02.2014
 @ $Author$
 @ $Revision$
 */

/**
 * Обработчик сообщений из шины с информацией по кредитам (БКИ/TSM)
 */
public class PhizProxyCreditListener extends EjbListenerBase
{
	private final CompositePhizProxyCreditMessageParser phizProxyCreditMessageParser = new CompositePhizProxyCreditMessageParser();

	private final PhizProxyCreditMessageCoordinator phizProxyCreditModuleCoordinator = new PhizProxyCreditMessageCoordinator();

	@Override protected XmlMessageParser getParser()
	{
		return phizProxyCreditMessageParser;
	}

	@Override protected MessageCoordinator getMessageCoordinator()
	{
		return phizProxyCreditModuleCoordinator;
	}

	@Override public void ejbCreate() throws EJBException
	{
	}
}

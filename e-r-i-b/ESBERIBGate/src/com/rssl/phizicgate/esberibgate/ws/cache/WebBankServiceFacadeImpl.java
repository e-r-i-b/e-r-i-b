package com.rssl.phizicgate.esberibgate.ws.cache;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.MessageHead;
import org.w3c.dom.Document;

/**
 * @ author: filimonova
 * @ created: 28.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class WebBankServiceFacadeImpl extends AbstractService implements WebBankServiceFacade
{
	public WebBankServiceFacadeImpl(GateFactory factory)
	{
		super(factory);
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return MessagesCacheManagerImpl.getInstance();
	}

	public Document sendOfflineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException
	{
		return null;
	}

	public Document sendOfflineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException
	{
		return null;
	}

	public Document sendOnlineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException
	{
		return null;
	}

	public GateMessage createRequest(String nameRequest) throws GateException
	{
		return null;
	}

	public Document sendOnlineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException
	{
		return null;
	}

	public GateFactory getFactory()
	{
		return null;
	}
}

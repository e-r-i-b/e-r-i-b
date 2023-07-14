package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizicgate.sbrf.ws.cache.MessagesCacheManagerImpl;
import org.w3c.dom.Document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gladishev
 * @ created 21.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class WebBankServiceFacadeSelector extends AbstractService implements WebBankServiceFacade
{
	private Map<String, WebBankServiceFacade> services;
	private MessagingConfig messagingConfig;

	public WebBankServiceFacadeSelector(GateFactory factory) throws GateException
	{
		super(factory);

		messagingConfig = ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG);

		Collection<MessageInfo> allMessageInfos = messagingConfig.getAllMessageInfos();
		services = new HashMap<String, WebBankServiceFacade>(allMessageInfos.size());
		String classPrefix = WebBankServiceFacade.class.getName() + ".";
		for (MessageInfo info : allMessageInfos)
		{
			String endpoint = info.getEndpoint();
			if(services.get(endpoint) == null)
				services.put(endpoint, (WebBankServiceFacade) getDelegate(classPrefix + endpoint));
		}
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return MessagesCacheManagerImpl.getInstance();
	}

	public Document sendOfflineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException
	{
		return getEndService(message).sendOfflineMessage(message, messageHead);
	}

	public Document sendOfflineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException
	{
		return getEndService(request.getDocument()).sendOfflineMessage(request, messageHead);
	}

	public Document sendOnlineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException
	{
		return getEndService(message).sendOnlineMessage(message, messageHead);
	}

	public GateMessage createRequest(String nameRequest) throws GateException
	{
		return getEndService(nameRequest).createRequest(nameRequest);
	}

	public Document sendOnlineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException
	{
		return getEndService(request.getDocument()).sendOnlineMessage(request, messageHead);
	}

	private WebBankServiceFacade getEndService(String nameRequest)
	{
		return services.get(messagingConfig.getMessageInfo(nameRequest).getEndpoint());
	}

	private WebBankServiceFacade getEndService(Document request)
	{
		return getEndService(request.getDocumentElement().getNodeName());
	}
}

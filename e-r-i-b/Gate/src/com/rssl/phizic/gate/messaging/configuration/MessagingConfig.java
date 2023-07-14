package com.rssl.phizic.gate.messaging.configuration;

import com.rssl.phizic.gate.messaging.configuration.generated.Request;
import com.rssl.phizic.gate.messaging.configuration.generated.Response;
import com.rssl.phizic.gate.messaging.configuration.generated.Attribute;

import java.util.*;

/**
 * @author Roshka
 * @ created 12.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class MessagingConfig
{
	private Map<String, MessageInfo> map = new HashMap<String, MessageInfo>();

	protected MessagingConfig()
	{
	}

	void addToMap(Request request)
    {

	    //noinspection unchecked
	    List<Response> responses = request.getResponses();
	    Set<String> responsesSet = new HashSet<String>();
	    //noinspection unchecked
	    List<Attribute> attributes = request.getAttributes();
	    for (Response response : responses)
	    {
		    responsesSet.add(response.getName());
	    }

	    Map<String, String> attributesMap = new HashMap<String, String>();
	    for(Attribute attribute : attributes)
	    {
		    attributesMap.put(attribute.getName(), attribute.getValue());
	    }
		MessageInfo newMessageInfo = new MessageInfo(request.getName(), request.getEndpoint(), responsesSet, request.getSubsystem(), request.getCacheKeyClass(), attributesMap);

		map.put(request.getName(), newMessageInfo);
    }

	/**
	 * @param request имя запроса
	 * @return информация о запросе
	 */
	public MessageInfo getMessageInfo(String request)
	{
		MessageInfo messageInfo = map.get(request);

		if(messageInfo == null)
			throw new RuntimeException("unknown request :" + request);

		return messageInfo;
	}

	/**
	 * @return список всех сообщний
	 */
	public Collection<MessageInfo> getAllMessageInfos()
	{
		return map.values();
	}

	public Set<String> avaibleRequests()
	{
		return map.keySet();
	}
}
package com.rssl.phizic.operations.locale;



import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.locale.replicator.ReplicaSource;

import java.util.Map;
import java.util.Set;

/**
 * @author koptyaev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class XmlStaticMessageSource implements ReplicaSource<Map.Entry<String, ERIBStaticMessage>>
{
	private Map<String, ERIBStaticMessage> messages;

	/**
	 * Конструктор репликаСорса из мапы
	 * @param messageMap мапа
	 */
	public XmlStaticMessageSource(Map<String, ERIBStaticMessage> messageMap)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		messages = messageMap;
	}
	public Set<Map.Entry<String, ERIBStaticMessage>> iterator()
	{
		return messages.entrySet();
	}
}

package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.clients.Client;

/**
 * 
 * @author Pankin
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountByNumberCacheKeyComposer extends StringCacheKeyComposer
{
	protected static final String SEPARATOR = "|";

	public String getKey(Object[] args, String params)
	{
		StringBuilder key = new StringBuilder();
		//получаем ключ по сущности
		key.append(super.getKey(args, params));
		//учитываем в идентифкаторк клиента
		key.append( SEPARATOR );
		Client client = (Client) args[0];
		key.append(client.getInternalOwnerId());
		return key.toString();
	}
}

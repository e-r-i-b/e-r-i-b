package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.SimpleClientIdGenerator;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Omeliyanchuk
 * @ created 01.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ClientProductsCacheComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		StringBuilder builder = new StringBuilder();
		//часть ключа по данным клиента
		builder.append(SimpleClientIdGenerator.generateClientId((Client) args[0]));

		//часть ключа по типам продуктов
		Class[] types = (Class[])args[1];

		Set<String> typesSet = new TreeSet<String>();
		for (Class type : types)
		{
			String name = type.getSimpleName();
			typesSet.add(name);
		}

		for (String type : typesSet)
		{
			builder.append("|");
			builder.append(type);
		}
		return builder.toString();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if (params[0] == null || !(params[0] instanceof Client))
			return null;
		return SimpleClientIdGenerator.generateClientId((Client) params[0]);
	}
}

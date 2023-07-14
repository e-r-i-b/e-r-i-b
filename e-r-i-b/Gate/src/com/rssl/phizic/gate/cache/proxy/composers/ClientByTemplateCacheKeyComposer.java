package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 09.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 *
 * Учитывает только firstName, surName, patrName, id, birthDay
 */
public class ClientByTemplateCacheKeyComposer extends AbstractCacheKeyComposer
{
	private static OfficeCacheKeyComposer officeComposer = new OfficeCacheKeyComposer();
	public Serializable getKey(Object[] args, String params)
	{
		StringBuilder builder = new StringBuilder();
		//Client client, Office office, int firstResult, int maxResults
		Client client = (Client)args[0];
		if(client==null)
			return null;

		if(!StringHelper.isEmpty(client.getId()))
			builder.append(client.getId());
		if(!StringHelper.isEmpty(client.getFirstName()))
			builder.append(client.getFirstName());
		if(!StringHelper.isEmpty(client.getSurName()))
			builder.append(client.getSurName());
		if(!StringHelper.isEmpty(client.getPatrName()))
			builder.append(client.getPatrName());
		if (client.getBirthDay() != null)
			builder.append(DateHelper.toISO8601DateFormat(client.getBirthDay()));

		String office = officeComposer.getKey(args, "1");
		if(!StringHelper.isEmpty(office))
			builder.append(office);

		int first = (Integer)args[2];
		int max = (Integer)args[3];

		builder.append(first);
		builder.append(max);

		return builder.toString();
	}
}


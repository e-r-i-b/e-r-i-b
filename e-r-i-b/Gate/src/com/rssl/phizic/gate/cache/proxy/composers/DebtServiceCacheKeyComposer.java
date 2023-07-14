package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;

import java.io.Serializable;
import java.util.List;

/**
 * User: Moshenko
 * Date: 20.10.2010
 * Time: 15:22:10
 */
public class DebtServiceCacheKeyComposer extends AbstractCacheKeyComposer
{
	private static final String SEPARATOR = "|";
	public Serializable getKey(Object[] args, String params)
	{
		StringBuilder builder = new StringBuilder();
		Recipient recipient = (Recipient)args[0];
		builder.append(recipient.getName());
		for (Field field : (List<Field>) args[1])
		{
			if (field.isKey())
			{
				builder.append(SEPARATOR);
				builder.append(field.getExternalId());
				builder.append(SEPARATOR);
				builder.append(field.getValue());
			}
		}
		return builder.toString();
    }

}

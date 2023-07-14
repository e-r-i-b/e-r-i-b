package com.rssl.phizic.auth.modes.readers;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.auth.modes.iPasCompositeConfirmResponse;

import java.util.Set;

/**
 * Author: Moshenko
 * Date: 05.05.2010
 * Time: 12:14:24
 */
public class iPasCompositeConfirmResponseReader extends CompositeConfirmResponseReader
{
	public boolean read()
	{
		boolean read = true;
		Set<ConfirmStrategyType> types = readers.keySet();
		byte count=0;
		for (ConfirmStrategyType type : types)
		{
				read = readers.get(type).read();
				if (read)
				{
					count++;
				}
		}
		//если read выполнился хотябы для одной стратегии
		if (count > 0)
			return true;
		else
			return false;
	}

	public ConfirmResponse getResponse()
		{
			iPasCompositeConfirmResponse ipasCompositeConfirmResponse = new iPasCompositeConfirmResponse();
			Set<ConfirmStrategyType> types = readers.keySet();
			for (ConfirmStrategyType type : types)
			{
				ConfirmResponse confirmResponse = readers.get(type).getResponse();
				ipasCompositeConfirmResponse.addConfirmResponse(type, confirmResponse);
			}
			return ipasCompositeConfirmResponse;
		}
}

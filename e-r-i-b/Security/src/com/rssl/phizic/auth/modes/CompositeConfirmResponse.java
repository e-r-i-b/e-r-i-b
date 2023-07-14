package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * @author emakarov
 * @ created 18.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CompositeConfirmResponse implements SignatureConfirmResponse
{
	Map<ConfirmStrategyType, ConfirmResponse> responses;

	public CompositeConfirmResponse()
	{
		responses = new HashMap<ConfirmStrategyType, ConfirmResponse>();
	}

	public void addConfirmResponse(ConfirmStrategyType type, ConfirmResponse response)
	{
		responses.put(type, response);
	}

	public ConfirmResponse getConfirmResponse(ConfirmStrategyType type)
	{
		return responses.get(type);
	}

	/**
	 * @return подпись
	 */
	public String getSignature()
	{
		Set<ConfirmStrategyType> strategyTypes = responses.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			ConfirmResponse confirmResponse = responses.get(type);
			if (confirmResponse instanceof CryptoConfirmResponse)
			{
				return ((CryptoConfirmResponse) confirmResponse).getSignature();
			}
		}
		return null;
	}
}

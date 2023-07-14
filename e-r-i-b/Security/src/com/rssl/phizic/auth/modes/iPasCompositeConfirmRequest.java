package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * Author: Moshenko
 * Date: 28.04.2010
 * Time: 18:16:13
 */
public class iPasCompositeConfirmRequest extends CompositeConfirmRequest
{
	public void remConfirmRequest(ConfirmStrategyType type)
	{
		requests.remove(type);
	}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.conditionComposite;
	}


}

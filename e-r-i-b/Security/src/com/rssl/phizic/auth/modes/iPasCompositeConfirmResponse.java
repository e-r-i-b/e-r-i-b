package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * Author: Moshenko
 * Date: 05.05.2010
 * Time: 13:11:34
 */
public class iPasCompositeConfirmResponse extends CompositeConfirmResponse
{
	public void remConfirmResponse(ConfirmStrategyType type)
	{
		responses.remove(type);
	}
}

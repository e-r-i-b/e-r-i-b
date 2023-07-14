package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * @author basharin
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushPasswordConfirmRequest extends OneTimePasswordConfirmRequest
{
	public PushPasswordConfirmRequest(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public PushPasswordConfirmRequest() {}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.push;
	}
}

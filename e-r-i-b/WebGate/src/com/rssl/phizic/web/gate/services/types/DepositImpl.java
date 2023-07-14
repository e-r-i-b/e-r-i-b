package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.gate.deposit.DepositState;
import com.rssl.phizgate.common.routable.DepositBase;

/**
 * @author Krenev
 * @ created 30.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepositImpl extends DepositBase
{
	public void setState(String state)
	{
		if (state == null || state.trim().length() == 0)
		{
			return;
		}
		this.state = Enum.valueOf(DepositState.class, state);
	}
}

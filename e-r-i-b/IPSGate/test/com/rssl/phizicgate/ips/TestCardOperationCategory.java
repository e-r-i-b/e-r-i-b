package com.rssl.phizicgate.ips;

import com.rssl.phizic.gate.ips.CardOperationCategory;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */
class TestCardOperationCategory implements CardOperationCategory
{
	private boolean income;

	///////////////////////////////////////////////////////////////////////////

	public boolean isIncome()
	{
		return income;
	}

	void setIncome(boolean income)
	{
		this.income = income;
	}
}

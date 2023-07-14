package com.rssl.phizicgate.ips;

import com.rssl.phizic.gate.ips.CardOperationType;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */
class TestCardOperationType implements CardOperationType
{
	private long code;

	private boolean cash;

	///////////////////////////////////////////////////////////////////////////

	public long getCode()
	{
		return code;
	}

	void setCode(long code)
	{
		this.code = code;
	}

	public boolean isCash()
	{
		return cash;
	}

	void setCash(boolean cash)
	{
		this.cash = cash;
	}
}

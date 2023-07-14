package com.rssl.phizic.business.loyaltyProgram.mock;

import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;

import java.math.BigDecimal;

/**
 * Объект-заглушка для программы лояльности
 * @author gladishev
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class MockLoyaltyProgram implements LoyaltyProgram, MockObject
{
	private String externalId; //идентификатор

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public BigDecimal getBalance()
	{
		return null;
	}

	public String getPhone()
	{
		return null;
	}

	public String getEmail()
	{
		return null;
	}
}

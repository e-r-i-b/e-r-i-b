package com.rssl.phizic.business.imaccounts;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountState;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class MockIMAccount implements IMAccount, MockObject
{
	private String id = "";

	private String number = "";

	///////////////////////////////////////////////////////////////////////////

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getName()
	{
		return null;
	}

	public Currency getCurrency()
	{
		return null;
	}

	public Money getBalance()
	{
		return null;
	}

	public String getAgreementNumber()
	{
		return null;
	}

	public Calendar getOpenDate()
	{
		return null;
	}

	public IMAccountState getState()
	{
		return null;
	}

	public Office getOffice()
	{
		return null;
	}

	public Money getMaxSumWrite()
	{
		return null;
	}

	public Calendar getClosingDate()
	{
		return null;
	}
}

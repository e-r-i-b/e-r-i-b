package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer;

/**
 * @author osminin
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockAccountJurIntraBankTransfer extends MockAbstractJurTransfer implements AccountJurIntraBankTransfer
{
	private String chargeOffAccount = EMPTY_STRING;

	public String getChargeOffAccount()
	{
		return chargeOffAccount;
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return null;
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffAccount = chargeOffAccount;
	}
}

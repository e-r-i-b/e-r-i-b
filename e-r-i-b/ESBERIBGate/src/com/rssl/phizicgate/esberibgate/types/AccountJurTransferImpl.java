package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountJurTransfer;
import com.rssl.phizic.gate.longoffer.LongOffer;

/**
 * @author osminin
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountJurTransferImpl extends AbstractJurTransferImpl implements AccountJurTransfer
{
	private String chargeOffAccount;

	public AccountJurTransferImpl()
	{
	}

	public AccountJurTransferImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

	public String getChargeOffAccount()
	{
		return chargeOffAccount;
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffAccount = chargeOffAccount;
	}
}

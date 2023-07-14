package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.AccountIntraBankPayment;

/**
 * @author osminin
 * @ created 23.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountIntraBankPaymentImpl extends AbstractPhizTransferImpl implements AccountIntraBankPayment
{
	private String chargeOffAccount;

	public AccountIntraBankPaymentImpl()
	{
	}

	public AccountIntraBankPaymentImpl(LongOffer longOffer) throws GateException
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

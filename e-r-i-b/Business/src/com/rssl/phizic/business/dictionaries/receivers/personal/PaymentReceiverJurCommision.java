package com.rssl.phizic.business.dictionaries.receivers.personal;

import com.rssl.phizic.business.dictionaries.PaymentReceiverJur;

/**
 * @author Gainanov
 * @ created 20.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentReceiverJurCommision extends PaymentReceiverJur
{
	private double commision;

	public double getCommision()
	{
		return commision;
	}

	public void setCommision(double commission)
	{
		this.commision = commission;
	}
}

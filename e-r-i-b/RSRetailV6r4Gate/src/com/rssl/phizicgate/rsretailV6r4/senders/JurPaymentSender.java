package com.rssl.phizicgate.rsretailV6r4.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.AccountRUSPayment;
import com.rssl.phizic.common.types.Money;

/**
 * @author niculichev
 * @ created 29.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class JurPaymentSender extends RUSPaymentSender
{
	public JurPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Money getAmount(AccountRUSPayment payment)
	{
		return payment.getDestinationAmount();
	}

}

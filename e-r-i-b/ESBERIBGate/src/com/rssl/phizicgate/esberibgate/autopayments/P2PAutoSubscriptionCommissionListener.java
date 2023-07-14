package com.rssl.phizicgate.esberibgate.autopayments;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;

/**
 * Слушатель комиссии.
 *
 * @author bogdanov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */

public interface P2PAutoSubscriptionCommissionListener
{
	public void onCommission(Long status, String statusDescriptio, Money commission, AutoSubscriptionClaim claim) throws GateLogicException, GateException;
}

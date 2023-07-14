package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author tisov
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 */

public class CardToCardAutoSubscriptionCommissionCalculator implements CommissionCalculator
{
	public void setParameters(Map<String, ?> params){}

	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		AutoSubscriptionClaim claim = (AutoSubscriptionClaim) document;
		claim.setCommission(getCommission(claim));
	}

	private Money getCommission(AutoSubscriptionClaim claim) throws GateException, GateLogicException
	{
		if (claim.isSameTB())
		{
			CurrencyService service = GateSingleton.getFactory().service(CurrencyService.class);
			return new Money(BigDecimal.ZERO, service.getNationalCurrency());
		}

		return null;
	}
}

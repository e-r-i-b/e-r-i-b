package com.rssl.phizicgate.rsretailV6r4.commission;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.claims.AccountClosingClaim;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;

/**
 * @author: Pakhomova
 * @created: 11.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class AccountClosingCommissionCalculator extends DefaultPaymentCommissionCalculator
{
	/**
	 *
	 * @return сумму списания
	 */
	public Money getChargeOffAmount(GateDocument document)
	{
		AccountClosingClaim payment = (AccountClosingClaim) document;
		return super.getChargeOffAmount(payment.getTransferPayment());
	}

	public String getChargeOffAccount(GateDocument document)
	{
		AccountClosingClaim doc = (AccountClosingClaim) document;
		return doc.getClosedAccount();
	}

	public String getReceiverAccount(GateDocument document)
	{
		AccountClosingClaim doc = (AccountClosingClaim) document;
		return super.getReceiverAccount(doc.getTransferPayment());
	}
}

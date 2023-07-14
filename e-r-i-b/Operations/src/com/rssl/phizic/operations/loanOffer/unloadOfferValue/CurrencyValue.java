package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */
class CurrencyValue extends AbstractDocumentLoanValue
{
	CurrencyValue(AbstractPaymentDocument loan, boolean mandatory, int commaCount)
	{
		super(loan, mandatory, commaCount);
	}

	CurrencyValue(AbstractPaymentDocument loan)
	{
		super(loan, true, 1);
	}

	public Object getValue()
	{
		return loan.getDestinationAmount().getCurrency().getCode();
	}

	public boolean isEmpty()
	{
		return loan.getDestinationAmount() == null ||
				loan.getDestinationAmount().getCurrency() == null ||
				loan.getDestinationAmount().getCurrency().getCode() == null ? true : false;
	}

	public String getMessage()
	{
		return "destination currency";
	}
}

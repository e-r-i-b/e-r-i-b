package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */
class AmountValue extends AbstractDocumentLoanValue
{
	boolean noCents = false;

	AmountValue(AbstractPaymentDocument loan, boolean mandatory, int commaCount)
	{
		super(loan, mandatory, commaCount);
	}

	AmountValue(AbstractPaymentDocument loan,boolean noCents)
	{
		super(loan, true, 1);
		this.noCents = noCents;
	}

	public Object getValue()
	{
		if (noCents)
			return loan.getDestinationAmount().getDecimal().intValue();
		else
			return loan.getDestinationAmount().getDecimal().toString();
	}

	public boolean isEmpty()
	{
		return loan.getDestinationAmount() == null ||
				loan.getDestinationAmount().getDecimal() == null ? true : false;
	}

	public String getMessage()
	{
		return "destination amount";
	}
}

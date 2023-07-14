package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */
class IdValue extends LoanValue
{
	IdValue(GateExecutableDocument loan, boolean mandatory, int commaCount)
	{
		super(loan, mandatory, commaCount);
	}

	IdValue(GateExecutableDocument loan)
	{
		super(loan, true, 1);
	}

	public Object getValue()
	{
		return loan.getDocumentNumber();
	}

	public boolean isEmpty()
	{
		return StringHelper.isEmpty(loan.getDocumentNumber());
	}

	public String getMessage()
	{
		return "'Номер заявки'";
	}
}
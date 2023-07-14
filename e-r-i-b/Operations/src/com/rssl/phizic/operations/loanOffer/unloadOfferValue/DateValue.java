package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */
class DateValue extends LoanValue
{
	DateValue(GateExecutableDocument loan, boolean mandatory, int commaCount)
	{
		super(loan, mandatory, commaCount);
	}

	DateValue(GateExecutableDocument loan)
	{
		super(loan, true, 1);
	}

	public Object getValue()
	{
		return DateHelper.formatDateToStringWithSlash(loan.getExecutionDate());
	}

	public boolean isEmpty()
	{
		return loan.getExecutionDate() == null ? true : false;
	}

	public String getMessage()
	{
		return "'Дата заявки'";
	}
}

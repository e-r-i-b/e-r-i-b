package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.utils.DateHelper;

/**
 * User: Moshenko
 * Date: 06.12.2011
 * Time: 10:49:58
 */
class DateTimeValue extends DateValue
{
	DateTimeValue(GateExecutableDocument loan, boolean mandatory, int commaCount)
	{
		super(loan, mandatory, commaCount);
	}

	DateTimeValue(GateExecutableDocument loan)
	{
		super(loan, true, 1);
	}

	public String getMessage()
	{
		return "'Дата время заявки'";
	}

	public Object getValue()
	{
			return DateHelper.formatDateToStringWithSlash2(loan.getExecutionDate());
	}

}

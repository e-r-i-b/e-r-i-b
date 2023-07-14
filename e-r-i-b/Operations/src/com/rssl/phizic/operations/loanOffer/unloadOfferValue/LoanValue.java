package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.business.documents.GateExecutableDocument;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */
abstract class LoanValue extends AbstractValue
{
	GateExecutableDocument loan;

	LoanValue(GateExecutableDocument loan, boolean mandatory, int commaCount)
	{
		super(mandatory, commaCount);
		this.loan = loan;
	}
}

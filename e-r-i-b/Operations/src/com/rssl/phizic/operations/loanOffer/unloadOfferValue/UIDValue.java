package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Moshenko
 * @ created 10.12.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Номер заявки
 * Используется OperationUID из документа
 */
class UIDValue extends LoanValue
{
	UIDValue(GateExecutableDocument loan)
	{
		super(loan, true, 1);
	}

	public Object getValue()
	{
		return loan.getOperationUID();
	}

	public boolean isEmpty()
	{
		return StringHelper.isEmpty(loan.getOperationUID());
	}

	public String getMessage()
	{
		return "'Номер заявки'";
	}
}

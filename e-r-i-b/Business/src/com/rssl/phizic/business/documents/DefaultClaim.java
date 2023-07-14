package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class DefaultClaim extends BusinessDocumentBase
{
	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public String getNextState()
	{
		throw new UnsupportedOperationException();
	}
}

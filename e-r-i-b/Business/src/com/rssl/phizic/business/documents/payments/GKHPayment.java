package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.UtilityPayment;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.common.forms.doc.TypeOfPayment;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class GKHPayment extends AbstractPaymentDocument implements UtilityPayment
{
	public Class<? extends GateDocument> getType()
	{
		return UtilityPayment.class;
	}

	public String getPayerId()
	{
		return null;//TODO realize
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.EXTERNAL_PAYMENT_OPERATION;
	}
}

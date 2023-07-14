package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.payments.ChargeOffPayment;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.common.forms.doc.TypeOfPayment;

/**
 * @author Egorova
 * @ created 15.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChargeOffPaymentImpl extends AbstractPaymentDocument implements ChargeOffPayment
{
	public Class<? extends GateDocument> getType()
	{
		return ChargeOffPayment.class;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}
}

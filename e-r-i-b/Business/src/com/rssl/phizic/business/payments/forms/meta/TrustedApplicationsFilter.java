package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.gate.claims.AccountChangeMinBalanceClaim;

/**
 * @ author: Vagin
 * @ created: 30.07.2013
 * @ $Author:
 * @ $Revision
 * Фильтр проверки является ли документ созданым в доверенном приложении. Доверенными считаются каналы ЕРИБ, ЕРМБ и АТМ
 */
public class TrustedApplicationsFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		if (!(stateObject instanceof GateExecutableDocument))
		{
			throw new DocumentException("Ожидается GateExecutableDocument");
		}

		GateExecutableDocument paymentDocument = (GateExecutableDocument) stateObject;

		return CreationType.atm == paymentDocument.getCreationType() || CreationType.internet == paymentDocument.getCreationType() || CreationType.sms == paymentDocument.getCreationType();
	}
}

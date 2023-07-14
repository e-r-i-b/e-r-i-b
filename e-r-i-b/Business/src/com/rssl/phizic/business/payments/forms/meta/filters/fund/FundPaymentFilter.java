package com.rssl.phizic.business.payments.forms.meta.filters.fund;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.RurPayment;

/**
 * @author osminin
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр платежей в рамках краудгифтинга
 */
public class FundPaymentFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException, DocumentLogicException
	{
		if (!(stateObject  instanceof RurPayment))
		{
			return false;
		}

		RurPayment payment = (RurPayment) stateObject;
		return payment.isFundPayment();
	}
}

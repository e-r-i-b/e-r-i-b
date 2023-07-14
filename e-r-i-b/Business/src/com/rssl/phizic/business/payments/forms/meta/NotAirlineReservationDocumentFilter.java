package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;

/**
 * @author vagin
 * @ created 20.06.2012
 * @ $Author$
 * @ $Revision$
 * Фильтр запрещающий выполнение хендлера, если платеж - оплата брони авиабилетов.
 */
public class NotAirlineReservationDocumentFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		return !DocumentHelper.isAirlineReservationPayment((AbstractPaymentDocument)stateObject);
	}
}

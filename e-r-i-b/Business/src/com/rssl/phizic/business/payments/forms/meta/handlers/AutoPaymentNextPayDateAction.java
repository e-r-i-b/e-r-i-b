package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.CreateAutoPaymentImpl;
import com.rssl.phizic.business.documents.payments.JurPayment;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 07.05.2013
 * @ $Author$
 * @ $Revision$
 * Хендлер установки даты ближайшего платежа в автоплатеж
 */
public class AutoPaymentNextPayDateAction extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH,1);
		if (document instanceof JurPayment)
		{
			JurPayment payment = (JurPayment) document;
			if (payment.getNextPayDate() == null)
			{
				payment.setNextPayDate(startDate);
			}
			if (payment.getStartDate() == null)
			{
				payment.setStartDate(startDate);
			}
		}
		else if (document instanceof CreateAutoPaymentImpl)
		{
			CreateAutoPaymentImpl payment = (CreateAutoPaymentImpl) document;
			if (payment.getStartDate() == null)
			{
				payment.setStartDate(startDate);
			}
		}
		else
		{
			throw new IllegalArgumentException("Некорректный тип документа " + document.getClass());
		}
	}
}

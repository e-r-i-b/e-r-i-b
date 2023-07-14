package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.utils.DateHelper;

/**
 * Установка дата следующего платежа
 * @author niculichev
 * @ created 23.05.14
 * @ $Author$
 * @ $Revision$
 */
public class InvoiceSubscriptionNextPayDateAction extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof CreateInvoiceSubscriptionPayment))
			throw new DocumentException("Некорректный тип документа, ожидался CreateInvoiceSubscriptionPayment, " + document.getClass());

		CreateInvoiceSubscriptionPayment payment = (CreateInvoiceSubscriptionPayment) document;

		switch (payment.getExecutionEventType())
		{
			case ONCE_IN_WEEK:
			{
				payment.setNextPayDate(DateHelper.getNearDateByWeek(payment.getNextPayDayOfWeek()));
				return;
			}

			case ONCE_IN_MONTH:
			{
				payment.setNextPayDate(DateHelper.getNearDateByMonth(payment.getNextPayDayOfMonth()));
				return;
			}

			case ONCE_IN_QUARTER:
			{
				payment.setNextPayDate(DateHelper.getNearDateByQuarter(payment.getNextPayMonthOfQuarter(), payment.getNextPayDayOfMonth()));
				return;
			}
			default:
				throw new DocumentException("Не верно задан тип собития для подписки на инвойсы");
		}
	}
}

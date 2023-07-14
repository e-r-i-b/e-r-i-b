package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;

/**
 * Кондишн, проверящий заполненность параметров подписки на инвойсы(true - параметры НЕ заполнены)
 * @author niculichev
 * @ created 31.05.14
 * @ $Author$
 * @ $Revision$
 */
public class NotFilledSubscriptionParametersCondition extends LongOfferBillingPaymentRequisitesSufficientCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!(stateObject instanceof CreateInvoiceSubscriptionPayment))
			throw new BusinessException("Ожидался CreateInvoiceSubscriptionPayment");

		return super.accepted(stateObject, stateMachineEvent)
				&& !isFilledSubscriptionParameters((CreateInvoiceSubscriptionPayment) stateObject);
	}

	private boolean isFilledSubscriptionParameters(CreateInvoiceSubscriptionPayment subscription)
	{
		ExecutionEventType eventType = subscription.getExecutionEventType();
		if(eventType == null)
			return false;

		switch (eventType)
		{
			case ONCE_IN_WEEK:
				return subscription.getNextPayDayOfWeek() != null;

			case ONCE_IN_MONTH:
				return subscription.getNextPayDayOfMonth() != null;

			case ONCE_IN_QUARTER:
				return subscription.getNextPayDayOfMonth() != null && subscription.getNextPayMonthOfQuarter() != null;

			default:
				return false;
		}
	}
}

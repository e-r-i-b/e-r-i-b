package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * Проверяет, является ли документ оплатой брони аэрофлота
 * @author gladishev
 * @ created 06.03.14
 * @ $Author$
 * @ $Revision$
 */
public class AirlineReservationPaymentCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		return (stateObject instanceof JurPayment) && FormConstants.AIRLINE_RESERVATION_PAYMENT_FORM.equals(((JurPayment) stateObject).getFormName());
	}
}

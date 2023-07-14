package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 19.05.2012
 * @ $Author$
 * @ $Revision$
 * Условие отказа заявки на редактирование/создание автоподписки
 * если дата начала действия платежа меньше текущей при подтверждении в КЦ.
 */
public class AutoSubStartDateExpiredCondition implements StateObjectCondition<BusinessDocument>
{
	public boolean accepted(BusinessDocument stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		AutoSubscriptionCondition autoSubCondition = new AutoSubscriptionCondition();

		if(autoSubCondition.accepted(stateObject, stateMachineEvent))
		{
			JurPayment payment = (JurPayment)stateObject;
			Calendar curDate = DateHelper.getCurrentDate();
			Calendar nextPayDate = payment.getNextPayDate();

			return curDate.compareTo(nextPayDate) > 0;
		}
		return false;
	}
}

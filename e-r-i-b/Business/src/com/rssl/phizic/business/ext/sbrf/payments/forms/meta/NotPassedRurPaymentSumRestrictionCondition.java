package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.payments.forms.meta.SumRestrictionHandler;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * @author gladishev
 * @ created 16.11.2014
 * @ $Author$
 * @ $Revision$
 *
 * Состояние на то, что документ не прошел проверку на ограничение суммы.
 * Когда документ попадает в это состояние эму присваивается статус ОТКАЗАН.
 */

public class NotPassedRurPaymentSumRestrictionCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof RurPayment))
			return false;

		RurPayment payment = (RurPayment) stateObject;
		if (!payment.isServiceProviderPayment())
			return false;

		try
		{
			SumRestrictionHandler restrictionHandler = new SumRestrictionHandler();
			restrictionHandler.process(stateObject, stateMachineEvent);
			return false;
		} catch (DocumentLogicException ex)
		{
			stateMachineEvent.addMessage(ex.getMessage());
			return true;
		} catch (DocumentException ex)
		{
			throw new BusinessException(ex);
		}
	}
}

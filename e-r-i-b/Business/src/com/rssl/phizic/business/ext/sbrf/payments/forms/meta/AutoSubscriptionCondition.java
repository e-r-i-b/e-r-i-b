package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * Является ли документ заявкой на автоплатеж с карты через шину
 *
 * @author khudyakov
 * @ created 26.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionCondition implements StateObjectCondition<BusinessDocument>
{
	public boolean accepted(BusinessDocument stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		IQWaveBillingPaymentCondition iqWaveConditionIQWave = new IQWaveBillingPaymentCondition();
		if (iqWaveConditionIQWave.accepted(stateObject, stateMachineEvent))
		{
			return false;
		}

		if (!(stateObject instanceof ConvertableToGateDocument))
		{
			return false;
		}

		GateDocument gateDocument = ((ConvertableToGateDocument) stateObject).asGateDocument();
		return ESBHelper.isAutoSubscriptionPayment(gateDocument);
	}
}

package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author niculichev
 * @ created 12.01.2011
 * @ $Author$
 * @ $Revision$
 * кондишен "является ли объект биллинговым платежом с карты для ESB"
 */
public class ESBBillingPaymentCondition implements StateObjectCondition
{
	private static final StateObjectCondition iqWaveBillingPaymentCondition = new IQWaveBillingPaymentCondition();

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (iqWaveBillingPaymentCondition.accepted(stateObject, stateMachineEvent))
		{
			//платеж в адрес IQW никогда не идет в шину.
			return false;
		}
		if (!(stateObject instanceof ConvertableToGateDocument))
		{
			return false;
		}
		GateDocument gateDocument = ((ConvertableToGateDocument) stateObject).asGateDocument();
		Class<? extends GateDocument> type = gateDocument.getType();

		if (!CardPaymentSystemPayment.class.isAssignableFrom(type))
		{
			return false;
		}

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) gateDocument;
		try
		{
			return ESBHelper.isESBSupported(payment);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}

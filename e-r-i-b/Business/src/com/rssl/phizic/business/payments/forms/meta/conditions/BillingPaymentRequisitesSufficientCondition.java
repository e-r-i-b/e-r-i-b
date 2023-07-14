package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 22.12.2010
 * @ $Author$
 * @ $Revision$
 * �������� �� �������� ������������� ���������� ������������ ������� ��� ������������� ������. 
 */

public class BillingPaymentRequisitesSufficientCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof GateExecutableDocument))
		{
			throw new BusinessException("�������� ��� ������� "+stateObject.getClass().getName());
		}
		GateDocument gateDocument = ((GateExecutableDocument) stateObject).asGateDocument();
		if (!AbstractPaymentSystemPayment.class.isAssignableFrom(gateDocument.getType()))
			throw new BusinessException("�������� ��� ������� - ��������� ��������� AbstractPaymentSystemPayment");

		AbstractPaymentSystemPayment paymentSystemPayment = (AbstractPaymentSystemPayment) gateDocument;
		//��������� ��������� ���������� ������������ ������� �������� ������� ������������� ��������� � �������
		return !StringHelper.isEmpty(paymentSystemPayment.getIdFromPaymentSystem());
	}
}

package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;

/**
 * ������� �� �������� ������������� ���������� ������������ ������������ ������� ��� ������������� ������
 * @author niculichev
 * @ created 13.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferBillingPaymentRequisitesSufficientCondition extends BillingPaymentRequisitesSufficientCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		// ���� ���������� ���������� ��� ������ � ����� ������� �����������
		return super.accepted(stateObject, stateMachineEvent) &&
				((stateObject instanceof AbstractLongOfferDocument) && ((AbstractLongOfferDocument) stateObject).isLongOffer());
	}
}

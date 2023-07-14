package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * �������� �� ��, ��� ������� � ����� ��������� �����������
 * @author niculichev
 * @ created 25.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class TransferExternalResourceCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!(stateObject instanceof BusinessDocument))
			throw new BusinessException("�������� BusinessDocument");

		return isExternalTransfer((BusinessDocument) stateObject);
	}

	/**
	 * �������� �� �������� ��������� � ����� ��������� �����������.
	 *
	 * @param document ��������.
	 * @return ��������� ��� ���.
	 */
	public static boolean isExternalTransfer(BusinessDocument document)
	{
		if(!(document instanceof AbstractAccountsTransfer))
			return false;

		AbstractAccountsTransfer transfer = (AbstractAccountsTransfer) document;
		ResourceType destinationType = transfer.getDestinationResourceType();

		return destinationType == ResourceType.EXTERNAL_ACCOUNT || destinationType == ResourceType.EXTERNAL_CARD;
	}
}

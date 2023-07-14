package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author krenev
 * @ created 19.03.2011
 * @ $Author$
 * @ $Revision$
 * �������� �� �������� �������� �� ����������� ������ �������� � IQW.
 */
public class IQWaveBillingPaymentCondition implements StateObjectCondition<BusinessDocument>
{
	/**
	 * ��������� ������� �������� �� ���������
	 * @param stateObject - ������, ��� �������� ��� ��������
	 * @return true ���� ����������� �������, false ���� ���
	 */
	public boolean accepted(BusinessDocument stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		try
		{
			return DocumentHelper.isIQWaveDocument(stateObject);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}

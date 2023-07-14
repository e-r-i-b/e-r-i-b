package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.DocumentOfflineException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.forms.meta.conditions.DelayedStateCondition;

/**
 * ������������ ��� ��-���� ��������, ������� �� ����� ���� ��������� �� ��������������� �����
 *  � �� ����� ���� ��������
 * @author sergunin
 * @ created 21.02.2014
 * @ $Author$
 * @ $Revision$
 */

public class JobDelayedStateHandler extends BusinessDocumentHandlerBase
{

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{

			DelayedStateCondition delayedStateCondition = new DelayedStateCondition();
			//���� �� ������������ ����� ������������� ��������� ���������.
			if (delayedStateCondition.accepted(document, stateMachineEvent))
				throw new DocumentOfflineException("�������� �� ����� ���� ��������� �� ��������������� �����.");
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

}

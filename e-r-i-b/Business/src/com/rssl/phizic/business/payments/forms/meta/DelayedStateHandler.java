package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.forms.meta.conditions.DelayedStateCondition;

/**
 * ������������ ��� ��-���� ��������, ������� �� ����� ���� ��������� � �� ������������ �����
 *  � �� ����� ���� ��������
 * @author egorova
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class DelayedStateHandler extends BusinessDocumentHandlerBase
{
	protected static String DEFAULT_ERROR_MESSAGE = "�������� �� ����� ���� ��������� �� ����������� ��������. ����������, ��������� �������� �����.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{

			DelayedStateCondition delayedStateCondition = new DelayedStateCondition();
			//���� �� ������������ ����� ������ ������� �� �����.
			if (delayedStateCondition.accepted(document, stateMachineEvent))
				throw new DocumentLogicException(getMessage(document));
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

	protected String getMessage(StateObject stateObject) throws DocumentException, DocumentLogicException
	{
		return DEFAULT_ERROR_MESSAGE;
	}
}

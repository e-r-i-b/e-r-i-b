package com.rssl.phizic.business.documents.templates.source;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.templates.stateMachine.conditions.TransferExternalResourceCondition;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.logging.LogThreadContext;

/**
 * ����� ��� �������� ������� �� ������������ �������
 * @author niculichev
 * @ created 11.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class NewQuicklyTemplateSource extends NewTemplateSource
{
	public NewQuicklyTemplateSource(Long paymentId, CreationType channelType, DocumentValidator... validators) throws BusinessException, BusinessLogicException
	{
		super(paymentId, channelType, validators);
	}

	@Override
	protected void fireEvent()
	{
		// ��� ������������� ������������ ����������
	}

	@Override
	protected State getInitialState(CreationType channelType) throws BusinessLogicException, BusinessException
	{
		switch (channelType)
		{
			case atm:
			{
				// ��� ���� � ����� ������� ��������� ��������������
				if (LogThreadContext.isChipCard())
				{
					return new State(StateCode.TEMPLATE.name());
				}
				break;
			}
			case internet:
			{
				return new State(StateCode.SAVED_TEMPLATE.name());
			}
		}
		//������� ������� ����������� ��������� � ������� WAIT_CONFIRM_TEMPLATE, ��� ��������� � ������� TEMPLATE
		return new State(new TransferExternalResourceCondition().accepted(getTemplate(), null) ? StateCode.WAIT_CONFIRM_TEMPLATE.name() : StateCode.TEMPLATE.name());
	}
}

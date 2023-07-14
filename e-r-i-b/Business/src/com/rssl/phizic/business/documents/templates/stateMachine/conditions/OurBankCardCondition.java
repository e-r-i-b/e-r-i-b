package com.rssl.phizic.business.documents.templates.stateMachine.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;

/**
 * @author akrenev
 * @ created 02.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� �������� �� ����� ������� �����, � ������, ���� ����� ��������� ����������
 */

public class OurBankCardCondition extends TemplateConditionBase<TemplateDocument>
{
	public boolean accepted(TemplateDocument stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof IndividualTransferTemplate))
			throw new BusinessException("�������� ��� ������� - ��������� IndividualTransferTemplate");

		return ((IndividualTransferTemplate)stateObject).isOurBankExternalCard();

	}
}

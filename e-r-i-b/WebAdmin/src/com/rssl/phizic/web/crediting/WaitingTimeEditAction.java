package com.rssl.phizic.web.crediting;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.crediting.EditWaitingTimeOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * ���� ��������� ������� �������� ��������� �������������� ����������� �� CRM
 * @author Nady
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 */
public class WaitingTimeEditAction extends EditPropertiesActionBase<EditWaitingTimeOperation>
{
	@Override
	protected EditWaitingTimeOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditWaitingTimeOperation.class);
	}
}

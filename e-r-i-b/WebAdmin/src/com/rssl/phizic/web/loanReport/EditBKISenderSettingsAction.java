package com.rssl.phizic.web.loanReport;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.loanreport.EditBKITimeOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * ���� �������������� �������� ������ ����� �������� �������� � ���
 * @author Puzikov
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBKISenderSettingsAction extends EditPropertiesActionBase<EditBKITimeOperation>
{
	@Override
	protected EditBKITimeOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditBKITimeOperation.class);
	}
}

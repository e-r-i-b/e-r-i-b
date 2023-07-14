package com.rssl.phizic.web.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.loans.loan.EditSettingsLoansOperation;

/**
 * ����� �������������� �������� ��� ��������
 * @author basharin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSettingsLoansAction extends EditPropertiesActionBase<EditSettingsLoansOperation>
{
	@Override
	protected EditSettingsLoansOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditSettingsLoansOperation.class);
	}
}

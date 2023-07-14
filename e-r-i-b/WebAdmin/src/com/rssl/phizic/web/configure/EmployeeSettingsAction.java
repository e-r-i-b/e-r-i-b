package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author shapin
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeSettingsAction extends EditPropertiesActionBase
{
	@Override protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EmployeeSettingsOperation","EmployeeSettingsService");
	}
}

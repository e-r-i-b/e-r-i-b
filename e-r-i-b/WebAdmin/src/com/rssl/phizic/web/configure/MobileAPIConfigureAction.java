package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Настройки mAPI
 * User: Moshenko
 * Date: 17.08.2012
 * Time: 13:01:24
 */
public class MobileAPIConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("MobileApiSettingsOperation");
	}
}

package com.rssl.phizic.web.ext.sbrf.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * User: Moshenko
 * Date: 06.09.2012
 * Time: 17:08:06
 */
public class MobileBankConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("MobileBankSettingsOperation");
	}
}

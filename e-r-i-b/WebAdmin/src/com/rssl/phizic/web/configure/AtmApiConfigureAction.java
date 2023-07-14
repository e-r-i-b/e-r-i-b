package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Настройки atmAPI
 * @author Dorzhinov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class AtmApiConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("AtmApiSettingsOperation");
	}
}

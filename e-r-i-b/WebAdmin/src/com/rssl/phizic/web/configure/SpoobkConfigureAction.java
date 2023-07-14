package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Настройки СПООБК
 * @ author: Gololobov
 * @ created: 13.02.14
 * @ $Author$
 * @ $Revision$
 */
public class SpoobkConfigureAction extends EditPropertiesActionBase
{
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("SPOOBKConfigureOperation");
	}
}

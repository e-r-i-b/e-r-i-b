package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Изменение настроек программы лояльности
 * @author lukina
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("LoyaltyProgramConfigureOperation");
	}
}

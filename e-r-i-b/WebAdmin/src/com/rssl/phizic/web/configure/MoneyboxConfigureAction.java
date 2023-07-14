package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Редактирование настроек копилки
 * author miklyaev
 * created 18.08.15
 * @ $Author$
 * @ $Revision$
 */
public class MoneyboxConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("MoneyboxConfigureOperation");
	}
}

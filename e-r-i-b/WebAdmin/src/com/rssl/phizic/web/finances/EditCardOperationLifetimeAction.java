package com.rssl.phizic.web.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Настройка времени жизни операций
 * @author Jatsky
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditCardOperationLifetimeAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditCardOperationLifetimeOperation");
	}
}

package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Ќастройки сообщени€ объ€сн€ющего максимальную сумму вклада
 * @author sergunin
 * @ created 09.01.14
 * @ $Author$
 * @ $Revision$
 */
public class AccountMessageConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("AccountMessageConfigureOperation");
	}
}

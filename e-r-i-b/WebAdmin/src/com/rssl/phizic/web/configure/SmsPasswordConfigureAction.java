package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author eMakarov
 * @ created 17.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsPasswordConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("SetSmsSettingsOperation");
	}

	@Override
	protected EditPropertiesOperation getViewOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewSmsSettingsOperation");
	}
}

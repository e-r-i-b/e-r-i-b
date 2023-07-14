package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * User: IIvanova
 * Date: 14.02.2006
 * Time: 13:28:22
 */
public class PasswordCardsConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("SetCardSettingsOperation");
	}

	@Override
	protected EditPropertiesOperation getViewOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewCardSettingsOperation");
	}
}

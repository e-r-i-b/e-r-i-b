package com.rssl.phizic.web.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author osminin
 * @ created 14.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * экшен редактирования настроек уровня безопасности
 */
public class EditSettingsSecurityAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditSettingsSecurityOperation");
	}

	@Override
	protected PropertyCategory getCategory(EditFormBase form)
	{
		return PropertyCategory.CSABack;
	}
}

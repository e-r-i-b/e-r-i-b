package com.rssl.phizic.web.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author koptyaev
 * @ created 28.03.14
 * @ $Author$
 * @ $Revision$
 */
public class EditAutomaticRecategorizationAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("AutoReCategorizationSettingsOperation");
	}
}

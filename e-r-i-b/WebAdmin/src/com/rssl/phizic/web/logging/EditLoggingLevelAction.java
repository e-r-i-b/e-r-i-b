package com.rssl.phizic.web.logging;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.logging.EditLoggingLevelOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author eMakarov
 * @ created 23.03.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditLoggingLevelAction extends EditPropertiesActionBase<EditLoggingLevelOperation>
{
	@Override
	protected void initializeViewOperation(EditLoggingLevelOperation operation, EditPropertiesFormBase form) throws BusinessException, BusinessLogicException
	{
		operation.initialize(getCategory(form), form.getFieldKeys(), ((EditLoggingSettingsFormBase) form).getLogPrefix());
	}

	@Override
	protected EditLoggingLevelOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createViewOperation(frm);
	}

	protected EditLoggingLevelOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditLoggingLevelOperation.class);
	}
}

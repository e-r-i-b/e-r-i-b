package com.rssl.phizic.web.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author komarov
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class MailSettingsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("SetMailSettingsOperation");
	}
}

package com.rssl.phizic.web.dictionaries.pfp.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditDefaultPeriodSettingAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditDefaultPeriodSettingOperation");
	}
}

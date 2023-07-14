package com.rssl.phizic.web.dictionaries.pfp.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author akrenev
 * @ created 28.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTargetCountAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditTargetCountOperation");
	}
}

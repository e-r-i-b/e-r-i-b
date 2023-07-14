package com.rssl.phizic.web.dictionaries.pfp.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.dictionaries.pfp.configure.EditSegmentAvailableOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author akrenev
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSegmentAvailableAction extends EditPropertiesActionBase<EditSegmentAvailableOperation>
{
	@Override
	protected EditSegmentAvailableOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditSegmentAvailableOperation.class);
	}
}

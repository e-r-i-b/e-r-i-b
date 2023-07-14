package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author tisov
 * @ created 12.09.14
 * @ $Author$
 * @ $Revision$
 */

public class EditAutoPaymentsNearestDateRestrictionAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditAutoPaymentsNearestDateRestrictionOperation");
	}
}

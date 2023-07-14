package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author bogdanov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */

public class EditAutoPaymentsP2PNewCommissionAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditAutoPaymentsP2PNewCommissionOperation");
	}
}

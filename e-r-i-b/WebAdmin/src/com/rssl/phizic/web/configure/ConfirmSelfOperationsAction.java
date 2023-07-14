package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * экшн для настроек подтверджения операций между своими счетами.
 *
 * @author bogdanov
 * @ created 30.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmSelfOperationsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("ConfirmSelfOperationsOperation", "EditConfirmPaymentOperationsService");
	}

	@Override
	protected EditPropertiesOperation getViewOperation() throws BusinessException
	{
		try
		{
			return createOperation("ConfirmSelfOperationsOperation", "ViewConfirmPaymentOperationsService");
		}
		catch (BusinessException e) {
			return createOperation("ConfirmSelfOperationsOperation", "EditConfirmPaymentOperationsService");
		}
	}
}

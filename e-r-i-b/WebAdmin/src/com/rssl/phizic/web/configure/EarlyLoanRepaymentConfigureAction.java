package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Экшн настроек досрочного погашения кредита
 * User: petuhov
 * Date: 08.05.15
 * Time: 15:57 
 */
public class EarlyLoanRepaymentConfigureAction extends EditPropertiesActionBase
{

	private static final String OPERATION_KEY = "EarlyLoanPaymentSettingsOperation";

	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation(OPERATION_KEY);
	}
}

package com.rssl.phizic.web.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.deposits.EditSettingsDepositsOperation;

/**
 * Ёкшен редактировани€ настроек дл€ депозитов
 * @author basharin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSettingsDepositsAction extends EditPropertiesActionBase<EditSettingsDepositsOperation>
{
	@Override
	protected EditSettingsDepositsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditSettingsDepositsOperation.class);
	}

}
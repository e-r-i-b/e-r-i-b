package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Ќастройки реквизитов банка, которые не могут быть получены из справочников
 * @author Pankin
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class BankDetailsConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("BankDetailsConfigureOperation");
	}
}

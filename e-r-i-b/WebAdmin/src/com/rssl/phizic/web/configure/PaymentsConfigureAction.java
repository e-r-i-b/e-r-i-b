package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Экшен для настроек Переводов и платежей
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class PaymentsConfigureAction extends EditPropertiesActionBase
{
	@Override protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		if (PermissionUtil.impliesOperation("EditPaymentsConfigureOperation", "PaymentsConfigureService"))
			return createOperation("EditPaymentsConfigureOperation", "PaymentsConfigureService");
		else if (PermissionUtil.impliesOperation("EditPaymentsConfigureOperation", "PaymentsConfigureServiceEmployee"))
			return createOperation("EditPaymentsConfigureOperation", "PaymentsConfigureServiceEmployee");
		else
			return createOperation("EditPaymentsConfigureOperation");
	}
}

package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.operations.registration.ConnectUDBOSettingsOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * экшен для настройки удаленного подключения УДБО
 * @author basharin
 * @ created 30.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ConnectUDBOSettingsAction extends EditPropertiesActionBase
{
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("ConnectUDBOSettingsOperation","ConnectUDBOSettingsService");
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation op) throws Exception
	{
		super.updateFormAdditionalData(frm, op);
		ConnectUDBOSettingsForm form = (ConnectUDBOSettingsForm) frm;
		ConnectUDBOSettingsOperation operation = (ConnectUDBOSettingsOperation) op;
		form.setDepartments(operation.getDepartments());
		form.setClaimRulesList(operation.getClaimRulesList());
	}
}

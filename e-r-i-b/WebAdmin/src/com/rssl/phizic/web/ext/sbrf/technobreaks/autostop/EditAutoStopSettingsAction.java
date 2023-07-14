package com.rssl.phizic.web.ext.sbrf.technobreaks.autostop;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pankin
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditAutoStopSettingsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditAutoStopOperation");
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		List<String> systemTypes = new ArrayList<String>();
		for (AutoStopSystemType systemType : AutoStopSystemType.values())
			systemTypes.add(systemType.name());
		((EditAutoStopSettingsForm) frm).setSystemTypes(systemTypes);
	}
}

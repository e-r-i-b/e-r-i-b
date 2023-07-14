package com.rssl.phizic.web.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.menu.EditSettingsMenuOperation;

/**
 * Ёкшен редактировани€ настроек дл€ меню
 * @author basharin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSettingsMenuAction extends EditPropertiesActionBase<EditSettingsMenuOperation>
{
	@Override
	protected EditSettingsMenuOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditSettingsMenuOperation.class);
	}
}
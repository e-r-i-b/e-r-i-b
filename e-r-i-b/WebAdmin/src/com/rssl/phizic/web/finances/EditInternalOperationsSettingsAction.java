package com.rssl.phizic.web.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Редактирование настроек категории «Переводы между своими картами»
 * @author lepihina
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditInternalOperationsSettingsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditInternalOperationsSettingsOperation");
	}
}

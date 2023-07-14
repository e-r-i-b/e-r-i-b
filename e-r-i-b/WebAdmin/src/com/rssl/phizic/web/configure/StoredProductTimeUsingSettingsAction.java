package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 *
 * Служит для просмотра и редактирования времени в течении которого сохранённая информация по продуктам все
 * еще может быть использована.
 *
 * User: Balovtsev
 * Date: 08.11.2012
 * Time: 10:34:20
 */
public class StoredProductTimeUsingSettingsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditStoredProductTimeUsingSettingsOperation");
	}

	@Override
	protected EditPropertiesOperation getViewOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewStoredProductTimeUsingSettingsOperation");
	}
}

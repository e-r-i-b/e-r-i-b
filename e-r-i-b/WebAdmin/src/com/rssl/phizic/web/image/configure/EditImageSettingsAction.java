package com.rssl.phizic.web.image.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * @author akrenev
 * @ created 26.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * экшен настройки ограничений на загружаемые картинки
 */

public class EditImageSettingsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditImageSettingsOperation");
	}
}

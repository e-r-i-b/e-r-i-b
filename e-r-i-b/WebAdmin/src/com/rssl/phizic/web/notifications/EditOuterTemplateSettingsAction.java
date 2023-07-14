package com.rssl.phizic.web.notifications;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Ёкшен редактировани€ настроек внешнего шаблона
 * @author tisov
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */

public class EditOuterTemplateSettingsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditOuterTemplateOperation");
	}
}

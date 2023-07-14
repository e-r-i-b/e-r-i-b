package com.rssl.phizic.web.configure.gate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Экшен редактирования настроек для сервисов IQWave
 * @author basharin
 * @ created 18.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class SeviceIQWaveConfigureAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("SeviceIQWaveConfigureOperation");
	}
}
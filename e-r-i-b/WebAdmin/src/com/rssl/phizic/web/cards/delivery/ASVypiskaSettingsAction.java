package com.rssl.phizic.web.cards.delivery;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * экшн для настройки переключателя работы АС выписка.
 *
 * @author bogdanov
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */

public class ASVypiskaSettingsAction extends EditPropertiesActionBase
{
	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("EditASVypiskaActivityOperation");
	}
}

package com.rssl.phizic.operations.crediting;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.operations.config.EditPropertiesOperation;

/**
 * Операция настройки времени ожидания получения предодобренных предложений из CRM
 * User: Nady
 * Date: 16.01.15
 */
public class EditWaitingTimeOperation extends EditPropertiesOperation<Restriction>
{
	@Override
	public void save() throws BusinessException, BusinessLogicException
	{
		super.save();
	}
}

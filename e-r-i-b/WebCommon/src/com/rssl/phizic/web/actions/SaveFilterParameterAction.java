package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author chegodaev
 * @ created 26.06.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class SaveFilterParameterAction extends ListActionBase
{
	protected Map<String, Object> loadFilterParameters(ListFormBase form, ListEntitiesOperation operation)
			throws BusinessException
	{
		return SaveFilterParameterHelper.loadFilterParameters(form, operation);
	}

	/**
	 * Передать параметры фильтрации.
	 * Метод сохраняет их в БД (вместе с URL)
	 * @param filterParams параметры фильтрации
	 * @throws BusinessException
	 */
	protected void saveFilterParameters(Map<String, Object> filterParams) throws BusinessException
	{
		SaveFilterParameterHelper.saveFilterParameters(filterParams);
	}

}

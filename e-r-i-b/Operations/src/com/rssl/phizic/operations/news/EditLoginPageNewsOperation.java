package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.Constants;

/**
 * редактированеи новости в базе CSA
 * @author basharin
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditLoginPageNewsOperation extends EditNewsOperation
{
	protected String getInstanceName()
	{
		return Constants.DB_CSA;
	}

	@Override
	public void save() throws BusinessException
	{
		super.doSave();
		newsService.sendClearCacheAuthNewsEvent();
	}
}

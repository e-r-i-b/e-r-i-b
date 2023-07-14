package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;

/**
 * удаление новости из базы CSA
 * @author basharin
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class RemoveLoginPageNewsOperation extends RemoveNewsOperation
{
	protected String getInstanceName()
	{
		return Constants.DB_CSA;
	}

	@Override
	public void remove() throws BusinessException, BusinessLogicException
	{
		newsService.remove(news, getInstanceName());
	}
}

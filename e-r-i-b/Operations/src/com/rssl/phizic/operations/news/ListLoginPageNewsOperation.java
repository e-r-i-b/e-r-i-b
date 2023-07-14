package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.Constants;

/**
 * просмотр новостей в базе CSA
 * @author basharin
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListLoginPageNewsOperation extends ListNewsOperation
{
	protected String getInstanceName()
	{
		return Constants.DB_CSA;
	}
}

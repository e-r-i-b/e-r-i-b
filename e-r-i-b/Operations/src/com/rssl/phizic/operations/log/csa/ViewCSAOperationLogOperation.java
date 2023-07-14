package com.rssl.phizic.operations.log.csa;

import com.rssl.phizic.operations.log.DownloadUserLogOperation;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.query.BeanQuery;

/**
 * @author vagin
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 * Операция получений записей журнала действий пользователей ЦСА
 */
public class ViewCSAOperationLogOperation extends DownloadUserLogOperation
{
	protected String getInstanceName()
	{
		return Constants.CSA_DB_LOG_INSTANCE_NAME;
	}

	public Query createQuery(String name)
	{
		return new BeanQuery(this, ViewCSAOperationLogOperation.class.getName() +"."+ name, Constants.CSA_DB_LOG_INSTANCE_NAME);
	}
}

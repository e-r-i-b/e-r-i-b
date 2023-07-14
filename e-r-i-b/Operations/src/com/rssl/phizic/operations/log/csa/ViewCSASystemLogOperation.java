package com.rssl.phizic.operations.log.csa;

import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.log.DownloadSystemLogOperation;

/**
 * @author vagin
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 * Операция просмотра журнала системных действий ЦСА
 */
public class ViewCSASystemLogOperation extends DownloadSystemLogOperation
{
	public Query createQuery(String name)
	{
		return new BeanQuery(this, ViewCSASystemLogOperation.class.getName() +"."+ name, Constants.CSA_DB_LOG_INSTANCE_NAME);
	}
}

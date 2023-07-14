package com.rssl.phizic.operations.log.csa;

import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.log.DownloadSystemLogOperation;

/**
 * @author osminin
 * @ created 22.02.15
 * @ $Author$
 * @ $Revision$
 */
public class ViewGuestCSASystemLogOperation extends DownloadSystemLogOperation
{
	@Override
	public Query createQuery(String name)
	{
		return new BeanQuery(this, ViewGuestCSASystemLogOperation.class.getName() +"."+ name, Constants.CSA_DB_LOG_INSTANCE_NAME);
	}
}

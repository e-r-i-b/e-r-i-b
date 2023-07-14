package com.rssl.auth.csa.front.operations;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author basharin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class OperationBase
{
	public Query createQuery(String queryName, String instanceName)
    {
        return new BeanQuery(this, ClassHelper.getActualClass(this).getName() + "." + queryName, instanceName);
    }

	public Query createQuery(String queryName)
	{
		return  createQuery(queryName, "CSA2");
	}
}
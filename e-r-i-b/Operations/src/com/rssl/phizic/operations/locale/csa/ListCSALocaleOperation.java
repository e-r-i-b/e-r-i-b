package com.rssl.phizic.operations.locale.csa;

import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.locale.ListLocaleOperation;

import java.util.List;
import java.util.Map;

/**
 * Операция просмотра списка локалей в цса
 * @author koptyaev
 * @ created 12.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class ListCSALocaleOperation extends ListLocaleOperation
{
	private static final String CSA_INSTANCE_NAME = "CSA";

	@Override
	protected String getInstanceName()
	{
		return CSA_INSTANCE_NAME;
	}

	public Query createQuery(String name)
	{
		return new BeanQuery(this, ListLocaleOperation.class.getName() +"." + name, getInstanceName());
	}

}

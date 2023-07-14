package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.synchronization.ListConsiderMultiBlockOperation;

/**
 * @author Kosyakov
 * @ created 15.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class GetBankListOperation extends ListConsiderMultiBlockOperation
{
	@Override
	public Query createQuery(String name)
	{
		return MultiLocaleQueryHelper.getOperationQuery(this, name, getInstanceName());
	}
}

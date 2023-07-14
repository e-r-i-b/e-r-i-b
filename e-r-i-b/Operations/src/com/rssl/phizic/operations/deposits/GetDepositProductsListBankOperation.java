package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;

/**
 * @author Evgrafov
 * @ created 05.04.2007
 * @ $Author: lepihina $
 * @ $Revision: 58631 $
 */

public class GetDepositProductsListBankOperation extends GetDepositProductsListOperationBase
{
	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
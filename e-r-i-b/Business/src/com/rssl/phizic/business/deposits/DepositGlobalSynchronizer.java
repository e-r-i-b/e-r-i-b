package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;

/**
 * @author Roshka
 * @ created 10.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositGlobalSynchronizer
{
	private static DepositProductService service = new DepositProductService();

	private DepositGlobal global;

	public DepositGlobalSynchronizer(DepositGlobal global)
	{
		this.global = global;
	}

	/**
	 * Обновить общие данные о ДП
	 * @throws BusinessException
	 */
	public void update() throws BusinessException
	{
		service.setGlobal(global,getInstanceName());
	}

	private String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
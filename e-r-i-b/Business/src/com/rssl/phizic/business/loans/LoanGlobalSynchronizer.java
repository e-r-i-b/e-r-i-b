package com.rssl.phizic.business.loans;

import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.business.BusinessException;

/**
 * @author gladishev
 * @ created 15.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanGlobalSynchronizer
{
	private static LoanProductService service = new LoanProductService();

	private LoanGlobal global;

	public LoanGlobalSynchronizer(LoanGlobal global)
	{
		this.global = global;
	}

	/**
	 * Обновить общие данные о КП
	 * @throws BusinessException
	 */
	public void update() throws BusinessException
	{
		service.setGlobal(global);
	}
}

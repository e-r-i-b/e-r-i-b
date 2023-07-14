package com.rssl.phizic.operations.dictionaries.productRequirements;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.operations.dictionaries.synchronization.ListDictionaryEntityOperationBase;

import java.util.List;

/**
 * @author lepihina
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция получения списка депозитных продуктов
 */

public class ListProductRequirementsOperation extends ListDictionaryEntityOperationBase
{
	private static final DepositProductService depositProductService = new DepositProductService();

	/**
	 * 
	 * @return Список типов вкладов
	 * @throws BusinessException
	 */
	public List<DepositProduct> getAccountTypes() throws BusinessException
	{
		return depositProductService.getAllProducts(getInstanceName());
	}
}

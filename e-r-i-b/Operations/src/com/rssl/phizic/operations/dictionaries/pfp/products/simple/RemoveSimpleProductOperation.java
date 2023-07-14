package com.rssl.phizic.operations.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.products.simple.SimpleProductBase;
import com.rssl.phizic.operations.dictionaries.pfp.products.RemoveProductOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления простых продуктов
 */

public class RemoveSimpleProductOperation extends RemoveProductOperationBase<SimpleProductBase>
{
	@Override
	protected Class<SimpleProductBase> getProductClass()
	{
		return SimpleProductBase.class;
	}
}

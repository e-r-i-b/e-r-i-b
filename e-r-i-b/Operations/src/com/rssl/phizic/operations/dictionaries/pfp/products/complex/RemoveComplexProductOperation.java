package com.rssl.phizic.operations.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexProductBase;
import com.rssl.phizic.operations.dictionaries.pfp.products.RemoveProductOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления комплексных продуктов
 */

public class RemoveComplexProductOperation extends RemoveProductOperationBase<ComplexProductBase>
{
	@Override
	protected Class<ComplexProductBase> getProductClass()
	{
		return ComplexProductBase.class;
	}
}

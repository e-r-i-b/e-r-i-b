package com.rssl.phizic.operations.dictionaries.pfp.products.simple.pension;

import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.operations.dictionaries.pfp.products.RemoveProductOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������� ���������� ���������
 */

public class RemovePensionProductOperation extends RemoveProductOperationBase<PensionProduct>
{
	@Override
	protected Class<PensionProduct> getProductClass()
	{
		return PensionProduct.class;
	}
}

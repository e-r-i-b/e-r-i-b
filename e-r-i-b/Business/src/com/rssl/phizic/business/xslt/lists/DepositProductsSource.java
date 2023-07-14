package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.common.types.transmiters.Pair;

import java.util.List;
import java.util.Map;


/**
 * @ author: filimonova
 * @ created: 07.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * Депозитные продукты
 */
public class DepositProductsSource extends CachedEntityListSourceBase
{
	private static final DepositProductService service = new DepositProductService();
	private static final String DEPOSIT_PRODUCT_ID = "depositId";

	public DepositProductsSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException
	{
		DepositProduct product = service.findByProductId(Long.parseLong(params.get(DEPOSIT_PRODUCT_ID)));
		return convertToReturnValue(product.getDescription(), product);
	}
}

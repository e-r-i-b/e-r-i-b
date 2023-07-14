package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс данных ограничений для карт.
 *
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1377 $
 */

public class ListDepositPoductRestrictionData extends RestrictionData <DepositProductRestriction>
{
	private List<DepositProduct> products;

	public List<DepositProduct> getProducts()
	{
		return products;
	}

	public void setProducts(List<DepositProduct> products)
	{
		this.products = products;
	}

	public DepositProductRestriction buildRestriction() throws BusinessException
	{
		Set<Long> cards = new HashSet<Long>();

		for (DepositProduct product : products)
			cards.add(product.getId());

		return new DepositProductRestrictionImpl(cards);
	}
}
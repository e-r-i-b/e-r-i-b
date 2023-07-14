package com.rssl.phizic.business.pfp.portfolio;

import org.apache.commons.collections.Predicate;

/**
 * @author mihaylov
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фильтр для списка продуктов в портфеле клиента.
 * Пропускает продукты со статусами
 * ADD - продукт добавлен в портфель, но его добавление не подтверждено
 * SAVE - продукт сохранен в портфеле, никаких модификаций с ним не производилось
 */
public class NotSavedProductFilter implements Predicate
{
	public static final Predicate INSTANCE = new NotSavedProductFilter();

	public boolean evaluate(Object object)
	{
		if(object instanceof PortfolioProduct)
		{
			PortfolioProduct product = (PortfolioProduct) object;
			return product.getState() == PortfolioProductState.ADD ||
				   product.getState() == PortfolioProductState.SAVE;
		}
		throw new IllegalArgumentException("Неизвестный тип " + object + ". Ожидается PortfolioProduct");
	}
}

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
 * SAVE - продукт сохранен в портфеле, никаких модификаций с ним не производилось
 * DELETED - продукт удалили не форме редактирования портфеля, но удаление не подтвердили
 */
public class SavedProductFilter implements Predicate
{
	public static final Predicate INSTANCE = new SavedProductFilter();

	public boolean evaluate(Object object)
	{
		if(object instanceof PortfolioProduct)
		{
			PortfolioProduct product = (PortfolioProduct) object;
			return product.getState() == PortfolioProductState.SAVE ||
				   product.getState() == PortfolioProductState.DELETED;
		}
		throw new IllegalArgumentException("Неизвестный тип " + object + ". Ожидается PortfolioProduct");
	}

}

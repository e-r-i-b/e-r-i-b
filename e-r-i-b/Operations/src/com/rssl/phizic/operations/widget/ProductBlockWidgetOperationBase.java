package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.web.ProductWidgetBase;
import com.rssl.phizic.business.web.WidgetObjectVisibility;

import java.util.*;

/**
 * Базовая операция для продуктовых виджетов
 * @ author Rtischeva
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class ProductBlockWidgetOperationBase<TWidget extends ProductWidgetBase> extends WidgetOperation<TWidget>
{
	Map<Long, EditableExternalResourceLink> productsMap;
	///////////////////////////////////////////////////////////////////////////

	protected void actualizeHiddenProducts(List<? extends EditableExternalResourceLink> productLinks)
	{
		List<WidgetObjectVisibility> productsVisibility = getWidget().getProductsVisibility();

		productsMap = new LinkedHashMap<Long, EditableExternalResourceLink>();

		for (EditableExternalResourceLink productLink : productLinks)
		{
			productsMap.put(productLink.getId(), productLink);
		}

		for (EditableExternalResourceLink productLink : productLinks)
		{
			WidgetObjectVisibility visible = new WidgetObjectVisibility(productLink.getId(), true);
			WidgetObjectVisibility notVisible = new WidgetObjectVisibility(productLink.getId(), false);
			if (!(productsVisibility.contains(visible) || productsVisibility.contains(notVisible)))
				productsVisibility.add(new WidgetObjectVisibility(productLink.getId(), productLink.getShowInMain()));
		}

		for (Iterator<WidgetObjectVisibility> iterator = productsVisibility.iterator(); iterator.hasNext();)
		{
			WidgetObjectVisibility currentProduct = iterator.next();
			if (!productsMap.containsKey(currentProduct.getId()))
				iterator.remove();
		}

		getWidget().setProductsVisibility(productsVisibility);
	}

	/**
	 * Получить список id продуктов, которые отображаются в данном виджете
	 * @return список id
	 */
	public List<Long> getShowProducts()
	{
		List<WidgetObjectVisibility> productsVisibility = getWidget().getProductsVisibility();
		List<Long> showProducts = new LinkedList<Long>();
		for (WidgetObjectVisibility product : productsVisibility)
		{
			EditableExternalResourceLink productLink = productsMap.get(product.getId());
			if (product.isVisible())
				showProducts.add(productLink.getId());
		}
		return showProducts;
	}
}

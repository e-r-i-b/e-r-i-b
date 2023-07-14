package com.rssl.phizic.business.web;

import java.util.LinkedList;
import java.util.List;

/**
 * Базовый класс для продуктовых виджетов
 * @ author Rtischeva
 * @ created 27.11.2012
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"AbstractClassExtendsConcreteClass"})
public abstract class ProductWidgetBase extends Widget
{
	//Список видимости продуктов в данном виджете
	private List<WidgetObjectVisibility> productsVisibility =  new LinkedList<WidgetObjectVisibility>();

	public List<WidgetObjectVisibility> getProductsVisibility()
	{
		return productsVisibility;
	}

	public void setProductsVisibility(List<WidgetObjectVisibility> productsVisibility)
	{
		this.productsVisibility = productsVisibility;
	}

	@Override
	protected Widget clone()
	{
		ProductWidgetBase newWidget = (ProductWidgetBase) super.clone();
		newWidget.setProductsVisibility(productsVisibility);
		return newWidget;
	}
}

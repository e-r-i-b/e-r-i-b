package com.rssl.phizic.business.web;

/**
 * Виджет "События"
 * @ author Rtischeva
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class EventWidget extends Widget
{
	private static final String DEFAULT_NUMBER_OF_SHOW_ITEMS = "3";

	/**
	 * TODO: (виджеты) Почему String, а не int? Исполнитель Е. Ртищева
	 */
	private String numberOfShowItems = DEFAULT_NUMBER_OF_SHOW_ITEMS;

	public String getNumberOfShowItems()
	{
		return numberOfShowItems;
	}

	public void setNumberOfShowItems(String numberOfShowItems)
	{
		this.numberOfShowItems = numberOfShowItems;
	}

	@Override
	protected Widget clone()
	{
		return super.clone();
	}
}

package com.rssl.phizic.business.web;

/**
 * Виджет «Предложения банка»
 * @author Dorzhinov
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class OffersWidget extends Widget
{
	private static final int DEFAULT_NUMBER_OF_SHOW_ITEMS = 3;

	private int numberOfShowItems = DEFAULT_NUMBER_OF_SHOW_ITEMS;

	///////////////////////////////////////////////////////////////////////////

	public int getNumberOfShowItems()
	{
		return numberOfShowItems;
	}

	public void setNumberOfShowItems(int numberOfShowItems)
	{
		this.numberOfShowItems = numberOfShowItems;
	}

	@Override
	protected Widget clone()
	{
		return super.clone();
	}
}


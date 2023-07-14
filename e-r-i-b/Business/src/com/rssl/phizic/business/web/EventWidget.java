package com.rssl.phizic.business.web;

/**
 * ������ "�������"
 * @ author Rtischeva
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class EventWidget extends Widget
{
	private static final String DEFAULT_NUMBER_OF_SHOW_ITEMS = "3";

	/**
	 * TODO: (�������) ������ String, � �� int? ����������� �. �������
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

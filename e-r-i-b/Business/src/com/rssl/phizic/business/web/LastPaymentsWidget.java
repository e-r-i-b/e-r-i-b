package com.rssl.phizic.business.web;

/** ������ "��������� �������"
 * @ author Rtischeva
 * @ created 17.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LastPaymentsWidget extends Widget
{
	private static final int DEFAULT_NUMBER_OF_SHOW_ITEMS = 5;

	// ���������� ��������, ������� ����� ���������� � �������
	private int numberOfShowItems = DEFAULT_NUMBER_OF_SHOW_ITEMS;

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

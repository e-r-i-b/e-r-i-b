package com.rssl.phizic.business.pfp.portfolio;

import org.apache.commons.collections.Predicate;

/**
 * @author mihaylov
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ ��������� � �������� �������.
 * ���������� �������� �� ���������
 * SAVE - ������� �������� � ��������, ������� ����������� � ��� �� �������������
 * DELETED - ������� ������� �� ����� �������������� ��������, �� �������� �� �����������
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
		throw new IllegalArgumentException("����������� ��� " + object + ". ��������� PortfolioProduct");
	}

}

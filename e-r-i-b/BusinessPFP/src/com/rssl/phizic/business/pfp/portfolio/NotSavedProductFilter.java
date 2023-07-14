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
 * ADD - ������� �������� � ��������, �� ��� ���������� �� ������������
 * SAVE - ������� �������� � ��������, ������� ����������� � ��� �� �������������
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
		throw new IllegalArgumentException("����������� ��� " + object + ". ��������� PortfolioProduct");
	}
}

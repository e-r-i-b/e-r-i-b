package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.business.resources.external.ExternalResourceLinkBase;

import java.util.Comparator;

/**
 *
 *
 * @author basharin
 * @ created 25.01.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ���������� ������������ ��� ���������� ExternalResourceLinkBase �������� ���������� �� ����� (��. ENH034237)
 */
public class CurrencyComparator extends AbstractCompatator implements Comparator
{
	//��������� ���������� �����
	private static final String[] orderOfCurrency = {"RUB", "RUR", "USD", "EUR"};

	public int compare(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null || o2 == null)
		{
			return -1;
		}

		ExternalResourceLinkBase linkBase1 = (ExternalResourceLinkBase) o1;
		ExternalResourceLinkBase linkBase2 = (ExternalResourceLinkBase) o2;

		if (isObjectsEquals(linkBase1.getCurrency(), linkBase2.getCurrency()))
			return 0;//��� �����
		else if (getPriority(linkBase1) < getPriority(linkBase2))
			return -1;//���� �� � ������ �������� ������ ���� � ������� ���������� �����
		else if (getPriority(linkBase1) > getPriority(linkBase2))
			return 1;//���� �� � ������ �������� ������ ���� � ������� ���������� �����
		else if (linkBase1.getCurrency() == null)
			return -1;
		else if (linkBase2.getCurrency() == null)
		    return 1;
		else
			return linkBase1.getCurrency().getCode().compareToIgnoreCase(linkBase2.getCurrency().getCode()); //� ����� ��������� ����� ��� � �������. ���������� ����������������� �������� �����
	}

	/**
	 * ���������� ������� � ������� orderOfCurrency, ������� �������� ������ �����
	 * @param linkBase ���� �� ��� ����
	 * @return �������
	 */
	private int getPriority(ExternalResourceLinkBase linkBase)
	{
		for (int i = 0; i < orderOfCurrency.length; ++i)
			if (linkBase.getCurrency() != null && linkBase.getCurrency().getCode() != null
					&& linkBase.getCurrency().getCode().compareToIgnoreCase(orderOfCurrency[i]) == 0)
				return i;
		return orderOfCurrency.length; //��� ��� � ������. ����� ������ ���������.
	}
}

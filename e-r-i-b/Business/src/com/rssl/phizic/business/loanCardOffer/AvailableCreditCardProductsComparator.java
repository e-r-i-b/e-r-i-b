package com.rssl.phizic.business.loanCardOffer;

import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.loans.CurrencyRurUsdEuroComparator;

import java.util.Comparator;

/**
 * @author Dorzhinov
 * @ created 05.07.2011
 * @ $Author$
 * @ $Revision$
 * ����������� ��������, �������������� �� ���� ������ �� 3-� ���������:
 * 1.CreditCardCondition
 * 2.CreditCardProduct
 *
 * �� ����� �������� � ����� � ������ ������� �������� �� ������: �����, �������, ����
 */
public class AvailableCreditCardProductsComparator implements Comparator<Object[]>
{
	public int compare(Object array1[], Object array2[])
	{
		// ���������� �� ����� �������� (�� ���� �� �� � ��� �������� �������������, �� �� ������ ��������� � �������, ������� ����������
		switch(((CreditCardProduct) array1[1]).getName().compareTo(((CreditCardProduct) array2[1]).getName()))
		{
			case 0 :
				// ���������� �� �������: �����, �������, ����, ��� ��������� �� ��������
				CurrencyRurUsdEuroComparator currencyComparator = new CurrencyRurUsdEuroComparator();
				return currencyComparator.compare(((CreditCardCondition) array1[0]).getCurrency(), ((CreditCardCondition) array2[0]).getCurrency());
			case -1 :
				return -1;
			case 1 :
		        return 1;
			default:
				return 0;
		}
	}
}

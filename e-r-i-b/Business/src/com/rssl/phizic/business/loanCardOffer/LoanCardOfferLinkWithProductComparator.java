package com.rssl.phizic.business.loanCardOffer;

import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.loans.CurrencyRurUsdEuroComparator;
import com.rssl.phizic.business.offers.LoanCardOffer;

import java.util.Comparator;

/**
 * @author Mescheryakova
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 * ����������� ��������, �������������� �� ���� ������ �� 3-� ���������:
 * 1.CreditCardCondition
 * 2.LoanCardOffer
 * 3.CreditCardProduct
 *
 * �� ����� �������� � ����� � ������ ������� �������� �� ������: �����, �������, ����
 */

public class LoanCardOfferLinkWithProductComparator implements Comparator<ConditionProductByOffer>
{
	public int compare(ConditionProductByOffer cond1, ConditionProductByOffer cond2)
	{
		// ���������� �� ����� �������� (�� ���� �� �� � ��� �������� �������������, �� �� ������ ��������� � �������, ������� ����������
		switch (cond1.getName().compareTo(cond2.getName()))
		{
			case 0 :
				// ���������� �� �������: �����, �������, ����, ��� ��������� �� ��������
				CurrencyRurUsdEuroComparator currencyComparator = new CurrencyRurUsdEuroComparator();

				if (cond1.getMaxLimit().getCurrency().getCode().equals(cond2.getMaxLimit().getCurrency().getCode()))
					return (cond1.getMaxLimit().getDecimal().floatValue() >   cond2.getMaxLimit().getDecimal().floatValue()) ? 1 : -1;
				return currencyComparator.compare(cond1.getMaxLimit().getCurrency(), cond2.getMaxLimit().getCurrency());

			case -1 :
				return -1;
			case 1 :
		        return 1;
			default:
				return 0;
		}
	}
}

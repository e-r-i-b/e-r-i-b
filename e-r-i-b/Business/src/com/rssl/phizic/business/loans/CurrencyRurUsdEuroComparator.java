package com.rssl.phizic.business.loans;

import com.rssl.phizic.common.types.Currency;

import java.util.Comparator;

/**
 * @author Mescheryakova
 * @ created 10.06.2011
 * @ $Author$
 * @ $Revision$
 *
 *  * ��������� ������ ������� ������� �� ������: �����, �������, ����, ��������� �� ��������
 */

public class CurrencyRurUsdEuroComparator implements Comparator<Currency>
{
public int compare(Currency currency1, Currency currency2)
	{
		if (currency2 == null || currency1 == null)
			return 0;

		// ������ � ������� �����, �������, ����
		if (currency2.getNumber().equals("810") || currency2.getNumber().equals("643"))
			return 1;
		if (currency1.getNumber().equals("810") || currency1.getNumber().equals("643"))
			return -1;
		if (currency2.getNumber().equals("840"))
			return 1;
		if (currency1.getNumber().equals("840"))
			return -1;
		if (currency2.getNumber().equals("978"))
			return 1;
		if (currency1.getNumber().equals("978"))
			return -1;

		// ������ ��������� �� ��������
		return currency1.getName().compareTo(currency2.getName());
	}
}

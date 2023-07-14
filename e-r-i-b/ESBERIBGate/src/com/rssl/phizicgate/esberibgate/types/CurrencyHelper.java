package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author egorova
 * @ created 25.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyHelper
{
	/**
	 * ������ ��� ����, ����� � ����� ������� �������� ������ � RUR
	 * �������� ISO-��� �� ������� �� �� ������
	 * @return ISO-���  ������
	 */
	public static String getCurrencyCode(String currencyCode) throws GateLogicException
	{
		if (currencyCode == null)
			throw new GateLogicException("�� ������� ��� ������");
		if(currencyCode.equals("RUR"))
			return "RUB";
		return currencyCode;
	}
}
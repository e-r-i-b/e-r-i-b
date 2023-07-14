package com.rssl.phizgate.mobilebank;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 31.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class GateCardHelper
{
	/**
	 * ���������� ����� ����� � ������������� ����
	 * @param card - �����
	 * @return ����� ����� � ���� "XXX *** X XXXX"
	 */
	public static String hideCardNumber(Card card) {
		if (card == null)
			return "empty";

		return hideCardNumber(card.getNumber());
	}

	/**
	 * ���������� ����� ����� � ������������� ����
	 * @param cardNumber - ����� �����
	 * @return ����� ����� � ���� "XXXX XX** **** XXXX"
	 */
	public static String hideCardNumber(String cardNumber) {
		if (StringHelper.isEmpty(cardNumber))
			return "empty";
		return MaskUtil.getCutCardNumberForLog(cardNumber);
	}
}

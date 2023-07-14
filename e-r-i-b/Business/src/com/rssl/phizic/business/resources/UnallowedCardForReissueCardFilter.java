package com.rssl.phizic.business.resources;

import com.rssl.phizic.business.resources.external.CardFilterBase;
import com.rssl.phizic.business.resources.external.CreditCardFilter;
import com.rssl.phizic.business.resources.external.NotVirtualCardsFilter;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.security.PermissionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * ������, �� ������������ ����� �� �������� ����������:
 * 1)	��������� ����� (CardType= CC)
 * 2)	Instant Issue ����(Momentum). (UNICardType=7 ���  UNICardType=8 � UNIAcctType= F )
 * 3)	����� ���. (UNICardType=27)
 * 4)	����� ���100 (UNICardType=14 ��� UNICardType=15 ��� UNICardType=17 ��� UNICardType=18)
 *
 * ������ ������ ������������ ��� ����������� ����.
 *
 * @author bogdanov
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class UnallowedCardForReissueCardFilter extends CardFilterBase
{
	private final CreditCardFilter creditCardFilter = new CreditCardFilter();
	private final NotVirtualCardsFilter notVirtualCardsFilter = new NotVirtualCardsFilter();

	/**
	 * ��� ����� � ��� �����.
	 */
	private static final Map<String, String> cardTypes = new HashMap<String, String>();

    static
    {
	    //Instant Issue ����(Momentum). (UNICardType=7 ���  UNICardType=8 � UNIAcctType= F )
		cardTypes.put("7", "F");
	    cardTypes.put("8", "F");

	    //����� ���. (UNICardType=27)
	    cardTypes.put("27", null);

	    //����� ���100 (UNICardType=14 ��� UNICardType=15 ��� UNICardType=17 ��� UNICardType=18)
	    cardTypes.put("14", null);
	    cardTypes.put("15", null);
	    cardTypes.put("17", null);
	    cardTypes.put("18", null);
    }

	public boolean accept(Card card)
	{
		if (creditCardFilter.accept(card))
			return false;

		//�� ����������� ������ ������ ��������� ����������.
		if (!PermissionUtil.impliesService("ReIssueCardIncludeVirtualClaim") && !notVirtualCardsFilter.accept(card))
			return false;

		//�� �������������� ������ ������ ��������� ����������. 
		if (!card.isMain())
			return false;

		if (!cardTypes.containsKey(card.getUNICardType()))
			return true;

		String acctType = cardTypes.get(card.getUNICardType());
		if (acctType == null)
			return false;

		if (acctType.equals(card.getUNIAccountType()))
			return false;

		return true;
	}
}

package com.rssl.phizic.business.resources;

import com.rssl.phizic.business.resources.external.CardFilterBase;
import com.rssl.phizic.business.resources.external.CreditCardFilter;
import com.rssl.phizic.business.resources.external.NotVirtualCardsFilter;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.security.PermissionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Фильтр, не пропускающий карты со слеющими свойствами:
 * 1)	кредитные карты (CardType= CC)
 * 2)	Instant Issue карт(Momentum). (UNICardType=7 или  UNICardType=8 и UNIAcctType= F )
 * 3)	карты УЭК. (UNICardType=27)
 * 4)	карты ПРО100 (UNICardType=14 или UNICardType=15 или UNICardType=17 или UNICardType=18)
 *
 * Данный фильтр используется при перевыпуске карт.
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
	 * Тип карты и тип счета.
	 */
	private static final Map<String, String> cardTypes = new HashMap<String, String>();

    static
    {
	    //Instant Issue карт(Momentum). (UNICardType=7 или  UNICardType=8 и UNIAcctType= F )
		cardTypes.put("7", "F");
	    cardTypes.put("8", "F");

	    //карты УЭК. (UNICardType=27)
	    cardTypes.put("27", null);

	    //карты ПРО100 (UNICardType=14 или UNICardType=15 или UNICardType=17 или UNICardType=18)
	    cardTypes.put("14", null);
	    cardTypes.put("15", null);
	    cardTypes.put("17", null);
	    cardTypes.put("18", null);
    }

	public boolean accept(Card card)
	{
		if (creditCardFilter.accept(card))
			return false;

		//По виртуальным картам нельзя выполнять перевыпуск.
		if (!PermissionUtil.impliesService("ReIssueCardIncludeVirtualClaim") && !notVirtualCardsFilter.accept(card))
			return false;

		//По дополнительным картам нельзя выполнять перевыпуск. 
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

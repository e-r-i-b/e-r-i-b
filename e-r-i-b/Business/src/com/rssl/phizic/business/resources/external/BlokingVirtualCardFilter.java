package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.security.PermissionUtil;

/**
 * @ author gorshkov
 * @ created 04.03.14
 * @ $Author$
 * @ $Revision$
 */
public class BlokingVirtualCardFilter extends NotVirtualCardsFilter
{
	/**
	 * ¬озвращает true или false в зависимости от того, виртуальна€ это карта и разрешен ли доступ к ее блокировке.
	 * @param card провер€ема€ карта
	 * @return true - карта не виртуальна€ или разрешен доступ, false - карта виртуальна€ и не разрешен доступ
	 */
	public boolean accept(Card card)
	{
		return PermissionUtil.impliesService("BlockingCardIncludeVirtualClaim") || super.accept(card);
	}
}

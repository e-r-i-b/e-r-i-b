package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.gate.bankroll.Card;

/**
 * Для клиента авторизованного с помощью карты в приложении сотрудника необходимо использовать в создании автоплатежей
 * только ту карту, с помощью которой произведена авторизация.
 *
 * Фильтр разрешает использовать одну (если клиент авторизуется с карты) или ноль карт.
 *
 * @author bogdanov
 * @ created 20.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginedCardFilter extends CardFilterBase
{
	private CardFilterConjunction notCreditActiveNotVirtualCardFilter;

	public LoginedCardFilter()
	{
		notCreditActiveNotVirtualCardFilter = new CardFilterConjunction();
		notCreditActiveNotVirtualCardFilter.addFilter(new ActiveNotVirtualCardsFilter());
	}

	public LoginedCardFilter(boolean isCreditCardAllowed)
	{
		notCreditActiveNotVirtualCardFilter = new CardFilterConjunction();
		notCreditActiveNotVirtualCardFilter.addFilter(isCreditCardAllowed ? new ActiveNotVirtualCardsFilter() : new ActiveNotVirtualNotCreditCardFilter());
	}

	public boolean accept(Card card)
	{
		try
		{
			if (!notCreditActiveNotVirtualCardFilter.accept(card))
				return false;

			Login login = PersonHelper.getLastClientLogin();
			if (login.getLastUserVisitingMode() != UserVisitingMode.EMPLOYEE_INPUT_BY_CARD)
				return false;

			return login.getLastLogonCardNumber().equals(card.getNumber());
		}
		catch (BusinessException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}

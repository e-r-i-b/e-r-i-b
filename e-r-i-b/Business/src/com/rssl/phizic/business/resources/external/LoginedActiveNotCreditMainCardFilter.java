package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.gate.bankroll.Card;

/**
 * @author vagin
 * @ created 22.10.14
 * @ $Author$
 * @ $Revision$
 * Фильтр карт по коорым осуществлялся вход клиента в админке, для копилок.
 */
public class LoginedActiveNotCreditMainCardFilter extends ActiveNotCreditMainCardFilter
{
	public LoginedActiveNotCreditMainCardFilter()
	{
		super();
		addFilter(new CardFilterBase()
		{
			public boolean accept(Card card)
			{
				try
				{
					Login login = PersonHelper.getLastClientLogin();
					if (login.getLastUserVisitingMode() != UserVisitingMode.EMPLOYEE_INPUT_BY_CARD)
						return false;

					return login.getLastLogonCardNumber().equals(card.getNumber());
				}
				catch (Exception e)
				{
					log.error(e.getMessage(), e);
					return false;
				}
			}
		});
	}
}

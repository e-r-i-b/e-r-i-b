package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author vagin
 * @ created 03.03.15
 * @ $Author$
 * @ $Revision$
 * Фильтр активной не кредитной карты по которой залогинился клиент у сотрудника.
 */
public class LoginedActiveNotCreditCardFilter extends ActiveNotCreditCardFilter
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public LoginedActiveNotCreditCardFilter()
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

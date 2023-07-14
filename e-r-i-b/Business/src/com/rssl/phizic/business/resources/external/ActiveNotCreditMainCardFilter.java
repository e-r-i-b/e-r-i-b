package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author vagin
 * @ created 02.09.14
 * @ $Author$
 * @ $Revision$
 * Фильтр : активная, не кредитная, основная карта с доступным карточным счетом в ЕРИБ.
 */
public class ActiveNotCreditMainCardFilter extends CardFilterConjunction
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public ActiveNotCreditMainCardFilter()
	{
		addFilter(new NotCreditCardFilter());
		addFilter(new ActiveCardFilter());
		addFilter(new MainCardFilter());
		addFilter(new CardFilterBase()
		{
			public boolean accept(Card card)
			{
				try
				{
					BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
					Account account = GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(card));
					return account!=null;
				}
				catch (Exception e)
				{
					//ошибка-не удалось получить карточный счет, значить не пропускаем фильтром.
					log.error(e.getMessage(), e);
					return false;
				}
			}
		});
	}
}

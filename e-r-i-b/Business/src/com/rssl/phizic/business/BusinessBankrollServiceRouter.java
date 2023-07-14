package com.rssl.phizic.business;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.objects.CardWithRouteInfo;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис создан для маршрутизации поиска СКС по карте в зависимости от офиса карты.
 * @author egorova
 * @ created 20.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class BusinessBankrollServiceRouter extends com.rssl.phizicgate.manager.services.routable.bankroll.BankrollServiceImpl
{
	private static final DepartmentService departmentService = new DepartmentService();
	public BusinessBankrollServiceRouter(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Перебираем все карты, получаем у них офис, зашиваем routerInfo в ид карты.
	 * @param card - карта(ы) для получения СКС
	 * @return список карт и их СКС
	 */
	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{
		if (card.length == 0)
			super.getCardPrimaryAccount(card);
		GroupResult<Card, Account> result = new GroupResult<Card, Account>();
		List<Card> cardsToRoute = new ArrayList<Card>();
		for (int i = 0; i < card.length; i++)
		{
			try
			{
				Office office = card[i].getOffice();
				//Если мы вдруг не знаем подразделение, где открыт счет карты.
				if (office == null)
				{
					result.putException(card[i], new GateLogicException(GATE_CANNOT_GET_INFORMATION_EXCEPTION + card[i].getNumber()));
					continue;
				}
				Department department = departmentService.findByCode(office.getCode());
				//Если подразделение не подключено к нашей системе.
				if (department == null)
				{
					result.putException(card[i], new GateLogicException(GATE_CANNOT_GET_INFORMATION_EXCEPTION + card[i].getNumber()));
					continue;
				}
				String info = new OfficeWithoutRouteInfo(department).getRouteInfo();
				CardWithRouteInfo cardToRoute = new CardWithRouteInfo(card[i], info);
				cardsToRoute.add(cardToRoute);
			}
			catch (BusinessException e)
			{
				result.putException(card[i], new GateException(e));
			}
		}

		//Если по результатам прверки принадлежности офиса, удалили не все карты ))
		if (!cardsToRoute.isEmpty())
		{
			Card[] cardWithRoutInfo = new Card[cardsToRoute.size()];
			for (int i = 0; i < cardsToRoute.size(); i++)
			{
				cardWithRoutInfo[i] = cardsToRoute.get(i);
			}
			GroupResult<Card, Account> mainResult = super.getCardPrimaryAccount(cardWithRoutInfo);
			result.add(mainResult);
		}
		return result;
	}
}

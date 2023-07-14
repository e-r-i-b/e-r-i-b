package com.rssl.phizicgate.manager.services.routable;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizicgate.manager.services.IDHelper;
import com.rssl.phizicgate.manager.services.objects.AccountWithRouteInfo;
import com.rssl.phizicgate.manager.services.objects.AccountWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.CardWithRouteInfo;
import com.rssl.phizicgate.manager.services.objects.CardWithoutRouteInfo;

/**
 * @ author: filimonova
 * @ created: 16.11.2010
 * @ $Author$
 * @ $Revision$
 *
 * Добавляет маршрутизирующую информацию к объектам
 */
public class RouteInfoService
{
	private static final AdapterService adapterService = new AdapterService();

	/**
	 * Добавить маршрутизирующую информацию к сущности
	 * @param object - сущность
	 * @return объект маршрутизации
	 * @throws GateException, GateLogicException
	 * */
	public Object appendRouteInfo(Object object) throws GateException, GateLogicException
	{
		if (object instanceof Account)
			return appendRouteInfo((Account)object);
		if (object instanceof Card)
			return appendRouteInfo((Card)object);
		if (object instanceof String)
			return appendRouteInfo((String) object);
		return object;
	}

	/**
	 * Добавить маршрутизирующую информацию к счету
	 * @param account - счет
	 * @return объект маршрутизации
	 * @throws GateException, GateLogicException
	 * */
	public Account appendRouteInfo(Account account) throws GateException, GateLogicException
	{
		if (IDHelper.matchRoutableId(account.getId()))
			return account;
		String adapterUUID = findRouteInfo(account.getOffice());
		AccountWithoutRouteInfo accountWithoutRouteInfo = new AccountWithoutRouteInfo(account);
		AccountWithRouteInfo accountWithRouteInfo = new AccountWithRouteInfo(accountWithoutRouteInfo, adapterUUID);
		return accountWithRouteInfo;
	}

	/**
	 * Добавить маршрутизирующую информацию по карте
	 * @param card - карта
	 * @return объект маршрутизации
	 * @throws GateException, GateLogicException
	 * */
	public Card appendRouteInfo(Card card) throws GateException, GateLogicException
	{
		if (IDHelper.matchRoutableId(card.getId()))
			return card;
		String adapterUUID = findRouteInfo(ESBHelper.findOfficeByESBId(card.getId()));
		CardWithoutRouteInfo cardWithoutRouteInfo = new CardWithoutRouteInfo(card);
		CardWithRouteInfo cardWithRouteInfo = new CardWithRouteInfo(cardWithoutRouteInfo, adapterUUID);
		return cardWithRouteInfo;
	}

	/**
	 * Добавить маршрутизирующую информацию к массиву объектов
	 * @param objects - счета
	 * @return объекты маршрутизации
	 * @throws GateException, GateLogicException
	 * */
	public <T>T[] appendRouteInfo(T... objects) throws GateException, GateLogicException
	{
		for (int i = 0; i< objects.length; i++)
		{
			objects[i] = (T)appendRouteInfo(objects[i] );
		}
		return objects;
	}


	/**
	 * Добавить маршрутизирующую информацию к идентификатору
	 * @param str - внешний идентификатор
	 * @return <Идентификатор>|<routeInfo>
	 * @throws GateException, GateLogicException
	 * */
	public String appendRouteInfo(String str) throws GateException, GateLogicException
	{
		if (IDHelper.matchRoutableId(str))
			return str;
		String adapterUUID = findRouteInfo(ESBHelper.findOfficeByESBId(str));
		return IDHelper.storeRouteInfo(IDHelper.restoreOriginalId(str), adapterUUID);
	}

	private String findRouteInfo(Office office) throws GateException
	{
		return adapterService.getAdapterUUIDByOffice(office);
	}
}

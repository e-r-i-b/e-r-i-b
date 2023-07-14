package com.rssl.phizic.gate.autopayments;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.AutoSubPaymentCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.AutoSubscriptionCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.FullAbstractCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.ListAutoSubscriptionComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;

import java.util.Calendar;
import java.util.List;

/**
 * @author: vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 * Сервис для работы с шинными автоплатежами
 */
public interface AutoSubscriptionService extends Service
{
	/**
	 * Получшение списка шинных автоплатежей по списку карт
	 * @param cards список карт пользователя
	 * @param types - Список возможных состояний подписки(необязателен)
	 * @return список штнных автоплатежей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = ListAutoSubscriptionComposer.class, name="AutoSubscription.autoSubscriptions", cachingWithWaitInvoke = true)
	public List<AutoSubscription> getAutoSubscriptions(List<Card> cards, AutoPayStatusType ... types) throws GateException, GateLogicException;

	/**
	 * Получение детальной информации по подписке на автоплатеж.
	 * @param autoSubscriptions спсиок подписок на автоплатеж.
	 * @return список детальной информации.
	 */
	@Cachable(keyResolver = AutoSubscriptionCacheKeyComposer.class, name = "AutoSubscription.autoSubscriptionInfo")
	public GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> getAutoSubscriptionInfo(AutoSubscription... autoSubscriptions) throws GateLogicException, GateException;


	/**
	 * Получение детальной информации по подписке на автоплатеж.
	 * @param externald спсиок подписок на автоплатеж.
	 * @return список детальной информации.
	 */
	//@Cachable(keyResolver = AutoSubscriptionExternalIdCacheKeyComposer.class, name = "AutoSubscription.autoSubscriptionInfo")
	public GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> getAutoSubscription(String... externald) throws GateLogicException, GateException;

	/**
	 * Получение списка платежей по подписке.
	 *
	 * @param autoSubscription подписка.
	 * @param begin начальная дата.
	 * @param end конечная дата.
	 * @return список платежей.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = FullAbstractCacheKeyComposer.class, name = "AutoSubscription.sheduleReport")
	public List<ScheduleItem> getSheduleReport(AutoSubscription autoSubscription, Calendar begin, Calendar end) throws GateException, GateLogicException;

	/**
	 * возвращает исполненные платежи по идентификаторам.
	 *
	 * @param autoSubscription подписка на автоплатеж.
	 * @param ids идентификаторы исполненного платежа.
	 * @return идентификатор подписки и платеж.
	 */
	@Cachable(keyResolver = AutoSubPaymentCacheKeyComposer.class, resolverParams="1", name = "AutoSubscription.subscriptionPayments")
	public GroupResult<Long, AutoSubscriptionDetailInfo> getSubscriptionPayments(AutoSubscription autoSubscription, Long ... ids) throws GateException, GateLogicException;
}

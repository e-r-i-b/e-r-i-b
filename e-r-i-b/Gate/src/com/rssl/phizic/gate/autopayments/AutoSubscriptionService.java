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
 * ������ ��� ������ � ������� �������������
 */
public interface AutoSubscriptionService extends Service
{
	/**
	 * ���������� ������ ������ ������������ �� ������ ����
	 * @param cards ������ ���� ������������
	 * @param types - ������ ��������� ��������� ��������(������������)
	 * @return ������ ������ ������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = ListAutoSubscriptionComposer.class, name="AutoSubscription.autoSubscriptions", cachingWithWaitInvoke = true)
	public List<AutoSubscription> getAutoSubscriptions(List<Card> cards, AutoPayStatusType ... types) throws GateException, GateLogicException;

	/**
	 * ��������� ��������� ���������� �� �������� �� ����������.
	 * @param autoSubscriptions ������ �������� �� ����������.
	 * @return ������ ��������� ����������.
	 */
	@Cachable(keyResolver = AutoSubscriptionCacheKeyComposer.class, name = "AutoSubscription.autoSubscriptionInfo")
	public GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> getAutoSubscriptionInfo(AutoSubscription... autoSubscriptions) throws GateLogicException, GateException;


	/**
	 * ��������� ��������� ���������� �� �������� �� ����������.
	 * @param externald ������ �������� �� ����������.
	 * @return ������ ��������� ����������.
	 */
	//@Cachable(keyResolver = AutoSubscriptionExternalIdCacheKeyComposer.class, name = "AutoSubscription.autoSubscriptionInfo")
	public GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> getAutoSubscription(String... externald) throws GateLogicException, GateException;

	/**
	 * ��������� ������ �������� �� ��������.
	 *
	 * @param autoSubscription ��������.
	 * @param begin ��������� ����.
	 * @param end �������� ����.
	 * @return ������ ��������.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = FullAbstractCacheKeyComposer.class, name = "AutoSubscription.sheduleReport")
	public List<ScheduleItem> getSheduleReport(AutoSubscription autoSubscription, Calendar begin, Calendar end) throws GateException, GateLogicException;

	/**
	 * ���������� ����������� ������� �� ���������������.
	 *
	 * @param autoSubscription �������� �� ����������.
	 * @param ids �������������� ������������ �������.
	 * @return ������������� �������� � ������.
	 */
	@Cachable(keyResolver = AutoSubPaymentCacheKeyComposer.class, resolverParams="1", name = "AutoSubscription.subscriptionPayments")
	public GroupResult<Long, AutoSubscriptionDetailInfo> getSubscriptionPayments(AutoSubscription autoSubscription, Long ... ids) throws GateException, GateLogicException;
}

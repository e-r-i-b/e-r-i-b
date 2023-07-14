package com.rssl.phizic.gate.longoffer;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.FullAbstractCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.LongOfferByClientCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.LongOfferCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.LongOfferExternalIdCacheKeyComposer;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��������� ���������� �� ���������� ����������
 */
public interface LongOfferService extends Service
{
	/**
	 * ��������� ���������� ��������� �������
	 * @param client ������
	 * @return ������ ���������� ��������� ��� ������ ������, ���� ����������
	 */
	@Cachable(keyResolver = LongOfferByClientCacheKeyComposer.class, name = "LongOffer.clientsLongOffers", cachingWithWaitInvoke = true)
	public List<LongOffer> getClientsLongOffers(Client client) throws GateException, GateLogicException;

	/**
	 * ��������� ���������� �� ���������� ����������
	 * @param longOffers ���������� ���������
	 * @return ���������� � ���������� ���������, ���������� ��������� ����������
	 * � ���������� � ���������� ���������(�������� ������ ������������� LongOffer)
	 */
	@Cachable(keyResolver = LongOfferCacheKeyComposer.class, name = "LongOffer.longOfferInfo")
	public GroupResult<LongOffer, AbstractTransfer> getLongOfferInfo(LongOffer... longOffers);

	/**
	 * ��������� ������� ��������� ��������� �� ������
	 * @param longOffer ���������� ��������
	 * @param begin ���� ������ �������
	 * @param end ���� �����  �������
	 * @return ������� ��������� ���������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = FullAbstractCacheKeyComposer.class, name = "LongOffer.sheduleReport")
	public List<ScheduleItem> getSheduleReport(LongOffer longOffer, Calendar begin, Calendar end) throws GateException, GateLogicException;

	/**
	 * ��������� ��������� �� ��������������
	 * @param externalId ������������� ���������
	 * @return ������ ���������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = LongOfferExternalIdCacheKeyComposer.class, name = "LongOffer.longOffer")
	public GroupResult<String, LongOffer> getLongOffer(String... externalId);

	//TODO �� �� ���� ��� ������ ����������
	//TODO �� �� ���� ��� ������ ���������� ���������.
}

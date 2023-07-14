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
 * сервис получени€ информации по длительным поручени€м
 */
public interface LongOfferService extends Service
{
	/**
	 * получение длительных поручений клиента
	 * @param client клиент
	 * @return список длительных поручений или пустой список, если отсутсвуют
	 */
	@Cachable(keyResolver = LongOfferByClientCacheKeyComposer.class, name = "LongOffer.clientsLongOffers", cachingWithWaitInvoke = true)
	public List<LongOffer> getClientsLongOffers(Client client) throws GateException, GateLogicException;

	/**
	 * ѕолучение информации по длительным поручени€м
	 * @param longOffers длительные поручени€
	 * @return информаци€ о длительном поручении, включающа€ платежную информацию
	 * и информацию о параметрах поручени€(значени€ должны реализовывать LongOffer)
	 */
	@Cachable(keyResolver = LongOfferCacheKeyComposer.class, name = "LongOffer.longOfferInfo")
	public GroupResult<LongOffer, AbstractTransfer> getLongOfferInfo(LongOffer... longOffers);

	/**
	 * ѕолучение графика исполени€ поручени€ за период
	 * @param longOffer длительное поручени
	 * @param begin дата начала периода
	 * @param end дата конца  периода
	 * @return графика исполени€ поручени€
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = FullAbstractCacheKeyComposer.class, name = "LongOffer.sheduleReport")
	public List<ScheduleItem> getSheduleReport(LongOffer longOffer, Calendar begin, Calendar end) throws GateException, GateLogicException;

	/**
	 * ѕолучение поручени€ по идентификатору
	 * @param externalId идентификатор поручени€
	 * @return список поручений
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = LongOfferExternalIdCacheKeyComposer.class, name = "LongOffer.longOffer")
	public GroupResult<String, LongOffer> getLongOffer(String... externalId);

	//TODO по –ќ надо еще список отмененных
	//TODO по –ќ надо еще список исполенных поручений.
}

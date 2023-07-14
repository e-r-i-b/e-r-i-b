package com.rssl.phizic.gate.loyalty;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.LoyaltyProgramCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.LoyaltyProgramCardCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.LoyaltyProgramIdCacheKeyComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * Сервис для поличения информации по программе лояльности
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyProgramService extends Service
{
	/**
	 * получение информации по программе лояльности по хешу.
	 * @param externalId - хеш
	 * @return информация по программе лояльности клиента
	 */
	@Cachable(keyResolver = LoyaltyProgramIdCacheKeyComposer.class, name = "LoyaltyProgram.clientLoyaltyProgramByExternalId")
	public LoyaltyProgram getClientLoyaltyProgram(String externalId) throws GateException, GateLogicException;

	/**
	 * Получение списка операций по программе лояльности.
	 * (По спецификации будет возвращаться до 200 операций)
	 * @param loyaltyProgram - программа лояльности
	 * @return список операций по программе лояльности
	 */
	@Cachable(keyResolver = LoyaltyProgramCacheKeyComposer.class, name = "LoyaltyProgram.loyaltyOperationInfo")
	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException;

	/**
	 * получение списка спецпредложений.
	 * @param loyaltyProgram - программа лояльности
	 * @return список спецпредложений
	 */
	@Cachable(keyResolver = LoyaltyProgramCacheKeyComposer.class, name = "LoyaltyProgram.loyaltyOffers")
	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException;
}

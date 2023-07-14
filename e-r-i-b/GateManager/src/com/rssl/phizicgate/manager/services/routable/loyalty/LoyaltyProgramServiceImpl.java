package com.rssl.phizicgate.manager.services.routable.loyalty;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loyalty.LoyaltyOffer;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.List;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramServiceImpl extends RoutableServiceBase implements LoyaltyProgramService
{
	public LoyaltyProgramServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * получение информации по программе лояльности по идентификатору карты.
	 * @param externalId - идентификатор карты
	 * @return информация по программе лояльности клиента
	 */
	public LoyaltyProgram getClientLoyaltyProgram(String externalId) throws GateException, GateLogicException
	{
		return getDelegate().getClientLoyaltyProgram(externalId);
	}

	/**
	 * Получение списка операций по программе лояльности.
	 * (Судя по спецификации пока будет возвращаться лишь 10 последних операций.)
	 * @param loyaltyProgram - программа лояльности
	 * @return список операций по программе лояльности
	 */
	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		return getDelegate().getLoyaltyOperationInfo(loyaltyProgram);
	}

	/**
	 * получение списка спецпредложений.
	 * @param loyaltyProgram - программа лояльности
	 * @return список спецпредложений
	 */
	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		return getDelegate().getLoyaltyOffers(loyaltyProgram);
	}


	private LoyaltyProgramService getDelegate() throws GateLogicException, GateException
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
		{
			throw new GateLogicException("Не задана внешняя система для карточных переводов");
		}

		//при получении uuid адаптера делаем проверку на активность внешней системы
		String uuid = ExternalSystemHelper.getCode(adapter.getUUID());
		return GateManager.getInstance().getService(uuid, LoyaltyProgramService.class);
	}
}

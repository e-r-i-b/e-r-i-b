package com.rssl.phizicgate.manager.services.routablePersistent.loyalty;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loyalty.LoyaltyOffer;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizicgate.manager.services.objects.LoyaltyProgramWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.RouteInfoReturner;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

import java.util.List;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramServiceImpl extends RoutablePersistentServiceBase<LoyaltyProgramService> implements LoyaltyProgramService
{
	public LoyaltyProgramServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected LoyaltyProgramService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(LoyaltyProgramService.class);
	}

	/**
	 * получение информации по программе лояльности по идентификатору карты.
	 * @param externalId - идентификатор карты
	 * @return информация по программе лояльности клиента
	 */
	public LoyaltyProgram getClientLoyaltyProgram(String externalId) throws GateException, GateLogicException
	{
		RouteInfoReturner ri = removeRouteInfoString(externalId);
		return storeRouteInfo(endService(ri.getRouteInfo()).getClientLoyaltyProgram(ri.getId()), ri.getRouteInfo());
	}

	/**
	 * Получение списка операций по программе лояльности.
	 * (Судя по спецификации пока будет возвращаться лишь 10 последних операций.)
	 * @param loyaltyProgram - программа лояльности
	 * @return список операций по программе лояльности
	 */
	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		LoyaltyProgramWithoutRouteInfo lpi = removeRouteInfo(loyaltyProgram);
		return endService(lpi.getRouteInfo()).getLoyaltyOperationInfo(lpi);
	}

	/**
	 * получение списка спецпредложений.
	 * @param loyaltyProgram - программа лояльности
	 * @return список спецпредложений
	 */
	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		LoyaltyProgramWithoutRouteInfo lpi = removeRouteInfo(loyaltyProgram);
		return endService(lpi.getRouteInfo()).getLoyaltyOffers(lpi);
	}
}

package com.rssl.phizicgate.ips;

import com.rssl.phizic.common.types.exceptions.DatabaseTimeoutException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.TooManyDatabaseCursorsException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractDataSourceServiceGate;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.gate.ips.IPSExecutiveService;
import com.rssl.phizic.gate.ips.IPSReceiverService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.time.DateUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class IPSExecutiveServiceImpl extends AbstractDataSourceServiceGate implements IPSExecutiveService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * Стандартный конструктор шлюза
	 * @param factory фабрика шлюзов
	 */
	public IPSExecutiveServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected String getDataSourceName()
	{
		return ConfigFactory.getConfig(IPSConfig.class).getDataSourceName();
	}

	@Override
	protected System getSystem()
	{
		return System.IPS;
	}
	///////////////////////////////////////////////////////////////////////////

	public void executeCardOperationClaims(List<IPSCardOperationClaim> claims) throws GateException
	{
		if (claims.isEmpty())
			return;

		// 1. Разобьём заявки на пачки (по дате первой транзакции)
		Collection<ClaimPack> claimPacks = packClaims(claims);

		// 2. Запустим пачки заявок в обработку
		for (ClaimPack pack : claimPacks)
			try
			{
				executeClaimPack(pack);
			}
			catch (DatabaseTimeoutException e)
			{
				IPSReceiverService receiverService = getFactory().service(IPSReceiverService.class);
				receiverService.setTimeoutStatusClaims(claims);
				log.error("Ошибка таймаута запроса к IPS.", e);
			}
			catch (TooManyDatabaseCursorsException e)
			{
				log.error("Количество открытых курсоров превысило допустимый максимум.", e);
			}
			catch (SystemException e)
			{
				throw new GateException(e);
			}
			catch(Exception e)
			{
				log.error("При обработке пачки заявок на загрузку карточных операций из IPS произошла ошибка.", e);
			}
	}

	private Collection<ClaimPack> packClaims(Collection<IPSCardOperationClaim> claims)
	{
		// Разбиваем заявки на пачки так,
		// чтобы в одной пачке были заявки с одинаковой датой startDate (время не учитывается)
		Map<Calendar, ClaimPack> map = new HashMap<Calendar, ClaimPack>();
		for (IPSCardOperationClaim claim : claims)
		{
			Calendar packDate = DateUtils.truncate(claim.getStartDate(), Calendar.DATE);
			ClaimPack pack = map.get(packDate);
			if (pack == null) {
				pack = new ClaimPack(packDate);
				map.put(packDate, pack);
			}
			pack.addClaim(claim);
		}
		return map.values();
	}

	private void executeClaimPack(ClaimPack pack) throws SystemException
	{
		executeJDBCAction(new GetCardOperationsBatch(getFactory(), pack));
	}
}

package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.StandInExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.util.ApplicationUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * Работа с режимом Stand-In в МБК
 * @author Puzikov
 * @ created 26.09.14
 * @ $Author$
 * @ $Revision$
 */

public class StandInUtils
{
	private static final int STAND_IN_CODE = -1000;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * Проверка результата исполнения ХП МБК на ошибку Stand-In
	 * @param returnCode код из МБК
	 */
	public static void checkStandInAndThrow(int returnCode)
	{
		if (STAND_IN_CODE == returnCode)
		{
			log.error("МБК находится в режиме Stand-In (Код ошибки -1000).");
			throw new StandInExternalSystemException("По техническим причинам вы не можете выполнить данную операцию. Пожалуйста, повторите попытку позже.");
		}
	}

	/**
	 * Проверка кода возврата на соответствие кодам недоступности МБК
	 * @param returnCode код из МБК
	 */
	public static void registerMBOfflineEvent(int returnCode) throws GateException
	{
		if (!ApplicationUtil.isPhizIC())
			return;

		Set<Integer> offlineCodes = ConfigFactory.getConfig(MobileBankConfig.class).getOfflineCodes();
		if (CollectionUtils.isEmpty(offlineCodes))
			return;

		if (offlineCodes.contains(Integer.valueOf(returnCode)))
		{
			GateSingleton.getFactory().service(ExternalSystemGateService.class).addMBKOfflineEvent();
		}
	}

	public static void registerMBTimeOutEvent(Long timeDiff) throws GateException
	{
		Long maxTimeout = ConfigFactory.getConfig(MobileBankConfig.class).getMaxTimeout();
		if (maxTimeout == null)
			return;

		if (timeDiff > maxTimeout*1000)
		{
			GateSingleton.getFactory().service(ExternalSystemGateService.class).addMBKOfflineEvent();
		}
	}
}

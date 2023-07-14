package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.InactiveExternalServiceException;
import com.rssl.phizic.gate.exceptions.OfflineExternalServiceException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * @author akrenev
 * @ created 26.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * ’елпер-класс дл€ работы с режимами недоступности
 */

public class InactiveTypeHelper
{
	private static final String TIME_MARK_IN_MESSAGE = "<time>";
	private static final Pattern TIME_MARK_IN_MESSAGE_PATTERN = Pattern.compile(TIME_MARK_IN_MESSAGE);	
	private static final String DEFAULT_ERROR_MESSAGE = "—ервис временно недоступен. ѕопробуйте повторить попытку позже.";

	/**
	 * ¬озвращает сообщение о недоступности функционала
	 * @param stateConfig Ќастройки мониторинга статуса сервиса
	 * @return —ообщение клиенту, собранное по переданному конфигу
	 */
	public static String getClientMessage(MonitoringServiceGateStateConfig stateConfig)
	{
		if(stateConfig == null)
			return DEFAULT_ERROR_MESSAGE;

		// если шаблон не задан, то и возвращать нечего
		String messageText = stateConfig.getLocaledMessageText();
		if (StringHelper.isEmpty(messageText))
			return DEFAULT_ERROR_MESSAGE;

		// если врем€ вставл€ть не тужно, то так и отображаем
		if (!messageText.contains(TIME_MARK_IN_MESSAGE))
			return messageText;

		// если врем€ нужно вставить, но данных не хватает, значит ничего не можем вернуть
		Calendar deteriorationTime = stateConfig.getDeteriorationTime();
		Long recoveryTime = stateConfig.getRecoveryTime();
		if (deteriorationTime == null || recoveryTime == null)
			return DEFAULT_ERROR_MESSAGE;

		// вставл€ем в нужное место врем€
		Date date = DateUtils.addMilliseconds(deteriorationTime.getTime(), recoveryTime.intValue());
		return TIME_MARK_IN_MESSAGE_PATTERN.matcher(messageText).replaceAll(DateHelper.formatDateDependsOnSysDate(date));
	}

	/**
	 * јктивировать режим недоступности дл€ статуса на основании переданных настроек
	 * @param stateConfig Ќастройки мониторинга статуса сервиса
	 * @throws GateLogicException
	 */
	public static void activate(MonitoringServiceGateStateConfig stateConfig) throws GateLogicException
	{
		if(stateConfig == null)
			return;

		switch (stateConfig.getInactiveType())
		{
			case offline:  throw new OfflineExternalServiceException(getClientMessage(stateConfig));
			case inactive: throw new InactiveExternalServiceException(getClientMessage(stateConfig));
		}
	}
}

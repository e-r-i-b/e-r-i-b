package com.rssl.phizic.rsa.integration.ws;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.utils.DateHelper;
import org.apache.axis.client.Stub;

import java.util.Calendar;

/**
 * @author tisov
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс для обёртки взаимодействия с веб-сервисами
 */
public abstract class TransportProviderBase<STUB extends Stub, RQ, RS>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);


	protected abstract STUB createStub() throws RSAIntegrationException;

	protected abstract RS doSend(STUB stub, RQ request) throws RSAIntegrationException;

	protected abstract String getUrl();

	protected abstract int getTimeOut();

	/**
	 * Отправка запроса во внешнюю систему
	 * @param request - запрос
	 * @return ответ
	 * @throws RSAIntegrationException
	 */
	public RS send(RQ request) throws GateException
	{
		STUB stub = getTransport();
		Calendar start = Calendar.getInstance();

		try
		{
			//noinspection unchecked
			return doSend(stub, request);
		}
		catch (Exception e)
		{
			log.error("Получена ошибка отправки сообщения во Фрод Мониторинг.", e);
			throw new RSAIntegrationException(e);
		}
		finally
		{
			try
			{
				if (RSAConfig.getInstance().isNeedMessagesExchangeLogging())
				{
					AxisLoggerHelper.writeToLog(System.rsa, stub._getCall().getMessageContext(), DateHelper.diff(Calendar.getInstance(), start));
				}
			}
			catch (Exception ex)
			{
				log.error("Проблемы с записью в журнал сообщений", ex);
				throw new GateException(ex);
			}
		}
	}

	private STUB getTransport() throws RSAIntegrationException
	{
		STUB stub = createStub();
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, getUrl());
		stub.setTimeout(getTimeOut());
		return stub;
	}
}
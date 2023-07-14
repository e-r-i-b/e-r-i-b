package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.integration.ws.ActivityEngineTransportProvider;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringRequestSerializerBase;

/**
 * @author tisov
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс для сендеров, связанных с отправкой сообщений в ActivityEngine
 */
public abstract class ActivityEngineSenderBase<RQ, RS, ID extends InitializationData> extends FraudMonitoringSenderGeneralBase<RQ, ID>
{

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private RS response;

	protected abstract ActivityEngineTransportProvider<RQ, RS> getTransportProvider();

	protected abstract FraudMonitoringRequestSerializerBase getSerializer();

	/**
	 * @return Ответ от веб-сервиса ActivityEngine
	 */
	public RS getResponse()
	{
		return response;
	}

	public void send()
	{
		try
		{
			response = getTransportProvider().send(getRequest());
		}
		catch (RSAIntegrationException e)
		{
			log.error(e);
			if (getSendingType() == FraudMonitoringRequestSendingType.ONLINE)
			{
				saveNotification();
			}
			else
			{
				throw e;
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	public boolean isNull()
	{
		return false;
	}

	public String getRequestBody() throws GateException, GateLogicException
	{
		return getSerializer().serialize(getRequest());
	}
}

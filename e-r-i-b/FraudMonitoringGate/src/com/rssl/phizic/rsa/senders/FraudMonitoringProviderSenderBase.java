package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.actions.ResponseAction;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringRequestSerializerBase;

/**
 * Базовый класс сендеров во Фрод-мониторинг
 *
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс запросов во фрод-мониторинг через основной веб-сервис (AdaptiveAuthentication)
 */
public abstract class FraudMonitoringProviderSenderBase<ID extends InitializationData, RQ extends GenericRequest> extends FraudMonitoringSenderGeneralBase<RQ, ID>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private ResponseAction responseAction;

	protected abstract FraudMonitoringRequestSerializerBase getSerializer();

	/**
	 * @return обработчик запроса
	 */
	protected abstract ResponseAction createResponseAction() throws GateException, GateLogicException;

	public void send() throws GateLogicException
	{
		try
		{
			this.responseAction = createResponseAction();
			this.responseAction.send();
		}
		catch (RequireAdditionConfirmFraudGateException e)
		{
			if (RSAConfig.getInstance().isSystemEffectOnOperation())
			{
				throw e;
			}
			log.error(e.getMessage(), e);
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			if (RSAConfig.getInstance().isSystemEffectOnOperation())
			{
				throw e;
			}
			log.error(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			if (RSAConfig.getInstance().isSystemEffectOnOperation())
			{
				throw e;
			}
			log.error(e.getMessage(), e);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (RSAIntegrationException e)
		{
			//интеграционная ошибка
			log.error(e.getMessage(), e);

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
			log.error(e.getMessage(), e);
		}
	}

	public String getRequestBody() throws GateException, GateLogicException
	{
		return getSerializer().serialize(getRequest());
	}

	@Override
	protected String getClientTransactionId() throws GateLogicException, GateException
	{
		return getRequest().getIdentificationData().getClientTransactionId();
	}
}

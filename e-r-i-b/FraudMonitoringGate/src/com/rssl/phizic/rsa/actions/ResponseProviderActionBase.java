package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.context.Constants;
import com.rssl.phizic.rsa.integration.jaxb.RequestBuilder;
import com.rssl.phizic.rsa.integration.jaxb.generated.DeviceDataType;
import com.rssl.phizic.rsa.integration.jaxb.generated.Request;
import com.rssl.phizic.rsa.integration.jms.JMSReceiver;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.HashMap;
import java.util.Map;
import javax.jms.TextMessage;

/**
 * @author tisov
 * @ created 20.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый экшн отправки запросов в систему фрод-мониторинга
 */
public abstract class ResponseProviderActionBase<RQ extends GenericRequest, RS extends GenericResponse> implements ResponseProviderAction<RQ, RS>
{
	protected RQ request;

	public RQ getRequest()
	{
		return request;
	}

	/**
	 * Получить из очереди ответ по clientTransactionId
	 * @param clientTransactionId идентификатор клиентской транзакции
	 * @return ответ от ФМ
	 * @throws GateException
	 */
	protected Request getRequestFromQueue(String clientTransactionId) throws GateException
	{
		try
		{
			TextMessage message = JMSReceiver.receiveMessageFormQueue(clientTransactionId);
			if (message == null)
			{
				throw new GateException("По clientTransactionId = " + clientTransactionId + " не найден ответ от ФМ.");
			}

			return RequestBuilder.getInstance().append(message.getText()).build();
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	protected void handleTokens(DeviceDataType deviceData)
	{
		Map<String, String> data = new HashMap<String, String>();
		data.put(Constants.SYSTEM_DEVICE_TOKEN_PREFIX_NAME + Constants.DEVICE_TOKEN_FSO_PARAMETER_NAME, deviceData.getDeviceTokenFSO());
		data.put(Constants.SYSTEM_DEVICE_TOKEN_PREFIX_NAME + Constants.DEVICE_TOKEN_COOKIE_PARAMETER_NAME, deviceData.getDeviceTokenCookie());

		RSAContext.append(data);
	}
}

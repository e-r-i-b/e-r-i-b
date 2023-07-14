package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.notifications.FraudNotification;
import com.rssl.phizic.rsa.notifications.FraudNotificationService;
import com.rssl.phizic.rsa.senders.builders.offline.FraudMonitoringOfflineRequestBuilderBase;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import org.w3c.dom.Document;

/**
 * @author tisov
 * @ created 09.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс для всех сендер во фрод-мониторинг (объединяет AdaptiveAuthentication и ActivityEngine)
 */
public abstract class FraudMonitoringSenderGeneralBase<RQ, ID extends InitializationData> implements FraudMonitoringSender<RQ, ID>
{
	private Document requestXMLDocument;
	protected RQ request;

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected abstract String getClientTransactionId() throws GateLogicException, GateException;

	protected void saveNotification()
	{
		try
		{
			FraudNotificationService.getInstance().send(createFraudNotification(), getClientTransactionId());
		}
		catch (Exception e)
		{
			log.error("Не удалось сохранить оповещение для системы фрод-оповещения: " + e.getMessage());
		}
	}

	protected FraudNotification createFraudNotification() throws GateLogicException, GateException
	{
		return new FraudNotification(this.getRequestBody());
	}

	protected abstract FraudMonitoringOfflineRequestBuilderBase<RQ> getOfflineBuilder();

	protected RQ createOffLineRequest() throws GateException, GateLogicException
	{
		FraudMonitoringOfflineRequestBuilderBase<RQ> builder = getOfflineBuilder();
		builder.initialize(requestXMLDocument);
		return builder.build();
	}

	/**
	 * @param requestXMLDocument - сериализованный в xml-документ запрос для фрод-мониторинга
	 */
	public void setRequestXMLDocument(Document requestXMLDocument)
	{
		this.requestXMLDocument = requestXMLDocument;
	}

	/**
	 * @return Запрос, отправляемый данным сендером
	 */
	protected RQ getRequest() throws GateLogicException, GateException
	{
		if (request == null)
		{
			if (this.getSendingType() == FraudMonitoringRequestSendingType.ONLINE)
			{
				request = createOnLineRequest();
			}
			else if (this.getSendingType() == FraudMonitoringRequestSendingType.OFFLINE)
			{
				request = createOffLineRequest();
			}
			else
			{
				throw new IllegalStateException("Неизвестный тип отправки");
			}
		}
		return request;
	}

	/**
	 * Создать RSA-запрос. Здесь инкапсулируем нужный конструктор
	 * @return - запрос
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected abstract RQ createOnLineRequest() throws GateException, GateLogicException;

	public FraudMonitoringRequestSendingType getSendingType()
	{
		//по-умолчанию сендеры являются онлайн
		return FraudMonitoringRequestSendingType.ONLINE;
	}
}

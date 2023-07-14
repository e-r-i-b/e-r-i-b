package com.rssl.phizic.business.fraudMonitoring;

import com.rssl.phizic.business.fraudMonitoring.senders.documents.FraudMonitoringDocumentOfflineSender;
import com.rssl.phizic.rsa.senders.*;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestType;
import com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author tisov
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 * Фабрика сендеров оффлайн-отправки во фрод-мониторинг
 */
public class FraudMonitoringOfflineSendersFactory
{
	private static final FraudMonitoringOfflineSendersFactory INSTANCE = new FraudMonitoringOfflineSendersFactory();
	private static Map<FraudMonitoringRequestType, Class<? extends FraudMonitoringSenderGeneralBase>> factoryMap = createFactoryMap();

	/**
	 * @return инстанс фабрики
	 */
	public static FraudMonitoringOfflineSendersFactory getInstance()
	{
		return INSTANCE;
	}

	private static Map<FraudMonitoringRequestType, Class<? extends FraudMonitoringSenderGeneralBase>> createFactoryMap()
	{
		Map<FraudMonitoringRequestType, Class<? extends FraudMonitoringSenderGeneralBase>> result = new TreeMap<FraudMonitoringRequestType, Class<? extends  FraudMonitoringSenderGeneralBase>>();
		result.put(FraudMonitoringRequestType.UPDATE_ACTIVITY, OfflineUpdateActivitySender.class);
		result.put(FraudMonitoringRequestType.BY_DOCUMENT, FraudMonitoringDocumentOfflineSender.class);
		result.put(FraudMonitoringRequestType.NOTIFY_BY_EVENT, FraudMonitoringOfflineNotifySender.class);
		result.put(FraudMonitoringRequestType.ANALYZE_BY_EVENT, FraudMonitoringOfflineAnalyzeSender.class);

		return result;
	}

	private FraudMonitoringOfflineSendersFactory(){};

	/**
	 * Получить сендер по xml-документу
	 * @param document xml-документ с сериализованным запросом во фрод-мониторинг
	 * @return - сендер формирования запроса для веб-сервиса
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public FraudMonitoringSenderGeneralBase getSender(Document document) throws IllegalAccessException, InstantiationException
	{
		String requestTypeStringValue = XmlHelper.getSimpleElementValue(document.getDocumentElement(), RSARequestXMLDocumentConstants.REQUEST_KIND);
		FraudMonitoringRequestType requestType = FraudMonitoringRequestType.valueOf(requestTypeStringValue);

		Class<? extends FraudMonitoringSenderGeneralBase> senderClass = factoryMap.get(requestType);
		FraudMonitoringSenderGeneralBase sender = senderClass.newInstance();
		sender.setRequestXMLDocument(document);
		return sender;
	}
}

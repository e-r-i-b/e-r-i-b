package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.DocumentUpdater;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.mapping.ServiceMapping;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.longoffer.AccountIntraBankPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AccountRUSPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * хелпер, отределяющий метод интеграции
 */

public final class WaySelectorHelper
{
	public static final Class<CommissionCalculator> COMMISSION_CALCULATOR = ServiceMapping.COMMISSION_CALCULATOR;
	public static final Class<DocumentSender> DOCUMENT_SENDER             = ServiceMapping.DOCUMENT_SENDER;
	public static final Class<DocumentUpdater> DOCUMENT_UPDATER           = ServiceMapping.DOCUMENT_UPDATER;

	private static final Map<Class, Map<Class, WaySelector>> services = getNewServicesContainer();

	private static Map<Class, Map<Class, WaySelector>> getNewServicesContainer()
	{
		Map<Class, Map<Class, WaySelector>> map = new HashMap<Class, Map<Class, WaySelector>>();
		map.put(COMMISSION_CALCULATOR, new HashMap<Class, WaySelector>());
		map.put(DOCUMENT_SENDER,       new HashMap<Class, WaySelector>());
		map.put(DOCUMENT_UPDATER,      new HashMap<Class, WaySelector>());
		return map;
	}

	static
	{
		ServiceNameWaySelector serviceNameWaySelector = new ServiceNameWaySelector();

		registerSenderAndUpdater(ClientAccountsTransfer.class,           serviceNameWaySelector);
		registerSenderAndUpdater(AccountToCardTransfer.class,            serviceNameWaySelector);
		registerSenderAndUpdater(CardToAccountTransfer.class,            serviceNameWaySelector);
		registerSenderAndUpdater(AccountToIMATransfer.class,             serviceNameWaySelector);
		registerSenderAndUpdater(IMAToAccountTransfer.class,             serviceNameWaySelector);
		registerSenderAndUpdater(CardRUSPayment.class,                   serviceNameWaySelector);
		registerSenderAndUpdater(CardIntraBankPayment.class,             serviceNameWaySelector);
		registerSenderAndUpdater(AccountRUSPaymentLongOffer.class,       serviceNameWaySelector);
		registerSenderAndUpdater(AccountIntraBankPaymentLongOffer.class, serviceNameWaySelector);
		registerSenderAndUpdater(RefuseLongOffer.class,                  serviceNameWaySelector);
		registerSenderAndUpdater(LossPassbookApplicationClaim.class,     serviceNameWaySelector);
		registerSenderAndUpdater(CardBlockingClaim.class,                serviceNameWaySelector);

		registerSenderAndUpdater(InternalCardsTransfer.class,                         serviceNameWaySelector);
		registerSenderAndUpdaterAndCalculator(ExternalCardsTransferToOtherBank.class, serviceNameWaySelector);
		registerSenderAndUpdaterAndCalculator(ExternalCardsTransferToOurBank.class,   serviceNameWaySelector);

		registerSenderAndUpdaterAndCalculator(CardPaymentSystemPayment.class, new CardPaymentSystemPaymentDocumentWaySelector());
	}

	private WaySelectorHelper(){}

	/**
	 * использовать ли интеграцию через MQ для документа
	 * @param document документ
	 * @param serviceType тип сервиса
	 * @return использовать ли интеграцию через MQ для документа
	 */
	static boolean useMQDelegate(GateDocument document, Class serviceType) throws GateException
	{
		return getIntegrationMode(document, serviceType) == ExternalSystemIntegrationMode.MQ;
	}

	/**
	 * получить режим интеграции для документа
	 * @param document документ
	 * @param serviceType тип сервиса
	 * @return использовать ли интеграцию через MQ для документа
	 */
	public static ExternalSystemIntegrationMode getIntegrationMode(GateDocument document, Class serviceType) throws GateException
	{
		Class<? extends GateDocument> documentType = document.getType();
		String serviceName = ServiceMapping.getServiceName(document, serviceType);
		if (StringHelper.isEmpty(serviceName))
			return ExternalSystemIntegrationMode.WS;

		//noinspection unchecked
		return getWaySelector(documentType, serviceType).getIntegrationMode(document, serviceName);
	}

	private static WaySelector getWaySelector(Class<? extends GateDocument> documentType, Class service)
	{
		return services.get(service).get(documentType);
	}

	private static void registerCalculator(Class<? extends GateDocument> documentType, WaySelector waySelector)
	{
		services.get(COMMISSION_CALCULATOR).put(documentType, waySelector);
	}

	private static void registerSender(Class<? extends GateDocument> documentType, WaySelector waySelector)
	{
		services.get(DOCUMENT_SENDER).put(documentType, waySelector);
	}

	private static void registerUpdater(Class<? extends GateDocument> documentType, WaySelector waySelector)
	{
		services.get(DOCUMENT_UPDATER).put(documentType, waySelector);
	}

	private static void registerSenderAndUpdater(Class<? extends GateDocument> documentType, WaySelector waySelector)
	{
		registerSender(documentType, waySelector);
		registerUpdater(documentType, waySelector);
	}

	private static void registerSenderAndUpdaterAndCalculator(Class<? extends GateDocument> documentType, WaySelector waySelector)
	{
		registerSender(documentType, waySelector);
		registerUpdater(documentType, waySelector);
		registerCalculator(documentType, waySelector);
	}
}

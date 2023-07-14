package com.rssl.phizic.gate.documents.mapping;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.config.ExternalSystemConfig;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.DocumentUpdater;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AccountIntraBankPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AccountRUSPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 11.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Маппинг сервисов
 */

public final class ServiceMapping
{
	public static final Class<CommissionCalculator> COMMISSION_CALCULATOR = CommissionCalculator.class;
	public static final Class<DocumentSender> DOCUMENT_SENDER = DocumentSender.class;
	public static final Class<DocumentUpdater> DOCUMENT_UPDATER = DocumentUpdater.class;

	private static final Map<Class, Map<Class, String>> services = getNewServicesContainer();

	static
	{
		registerSenderAndUpdater(ClientAccountsTransfer.class,          "AccountToAccountTransfer");
		registerSenderAndUpdater(AccountToCardTransfer.class,           "AccountToCardTransfer");
		registerSenderAndUpdater(CardToAccountTransfer.class,           "CardToAccountTransfer");
		registerSenderAndUpdater(AccountToIMATransfer.class,            "AccountToIMATransfer");
		registerSenderAndUpdater(IMAToAccountTransfer.class,            "IMAToAccountTransfer");
		registerSenderAndUpdater(CardRUSPayment.class,                  "CardToAccountPhizIntraBankTransfer");
		registerSenderAndUpdater(CardIntraBankPayment.class,            "CardToAccountPhizOurBankTransfer");
		registerSenderAndUpdater(AccountRUSPaymentLongOffer.class,      "AccountToAccountIntraBankPaymentLongOffer");
		registerSenderAndUpdater(AccountIntraBankPaymentLongOffer.class,"AccountToAccountOurBankPaymentLongOffer");
		registerSenderAndUpdater(RefuseLongOffer.class,                 "RefuseLongOffer");
		registerSenderAndUpdater(LossPassbookApplicationClaim.class,    "StopDepositOperationClaim");
		registerSenderAndUpdater(CardBlockingClaim.class,               "BlockingCardClaim");

		registerSenderAndUpdater(ExternalCardsTransferToOtherBank.class,"CardToCardIntraBankTransfer");
		registerSenderAndUpdater(ExternalCardsTransferToOurBank.class,  "CardToCardOurBankTransfer");
		registerSenderAndUpdater(InternalCardsTransfer.class,           "CardToCardTransfer");

		registerCalculator(ExternalCardsTransferToOtherBank.class, "CardToCardIntraBankTransferCommission");
		registerCalculator(ExternalCardsTransferToOurBank.class,   "CardToCardOurBankTransferCommission");

		registerSenderAndUpdater(CardPaymentSystemPayment.class, "CardPaymentSystemPayment");
		registerCalculator(CardPaymentSystemPayment.class,       "CardPaymentSystemPayment");

		registerCalculator(ExternalCardsTransferToOurBankLongOffer.class,       "AutoSubscriptionClaim");
		registerCalculator(ExternalCardsTransferToOtherBankLongOffer.class,     "AutoSubscriptionClaim");
	}

	private ServiceMapping(){}

	/**
	 * получить имя сервиса
	 * @param document документ
	 * @param service тип сервиса
	 * @return имя сервиса
	 */
	public static String getServiceName(GateDocument document, Class service)
	{
		Class<? extends GateDocument> documentType = document.getType();

		if (ExternalCardsTransferToOtherBank.class.equals(documentType) && ((ExternalCardsTransferToOtherBank) document).getReceiverCardType() == ReceiverCardType.SB)
			documentType = ExternalCardsTransferToOurBank.class;

		return services.get(service).get(documentType);
	}

	/**
	 * @return конфиг внешних систем
	 */
	public static ExternalSystemConfig getConfig()
	{
		return ConfigFactory.getConfig(ExternalSystemConfig.class);
	}

	private static Map<Class, Map<Class, String>> getNewServicesContainer()
	{
		Map<Class, Map<Class, String>> map = new HashMap<Class, Map<Class, String>>();
		map.put(COMMISSION_CALCULATOR, new HashMap<Class, String>());
		map.put(DOCUMENT_SENDER,       new HashMap<Class, String>());
		map.put(DOCUMENT_UPDATER,      new HashMap<Class, String>());
		return map;
	}

	private static void registerSenderAndUpdater(Class<? extends GateDocument> documentType, String serviceName)
	{
		registerSender(documentType, serviceName);
		registerUpdater(documentType, serviceName);
	}

	private static void registerCalculator(Class<? extends GateDocument> documentType, String serviceName)
	{
		services.get(COMMISSION_CALCULATOR).put(documentType, serviceName);
	}

	private static void registerSender(Class<? extends GateDocument> documentType, String serviceName)
	{
		services.get(DOCUMENT_SENDER).put(documentType, serviceName);
	}

	private static void registerUpdater(Class<? extends GateDocument> documentType, String serviceName)
	{
		services.get(DOCUMENT_UPDATER).put(documentType, serviceName);
	}
}

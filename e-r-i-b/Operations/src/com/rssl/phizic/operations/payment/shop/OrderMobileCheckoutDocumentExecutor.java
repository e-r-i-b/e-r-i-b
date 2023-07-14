package com.rssl.phizic.operations.payment.shop;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.ExternalShopDocumentSource;
import com.rssl.phizic.business.einvoicing.ShopDocumentInBlockExecutorBase;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ShopListenerConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.einvoicing.OrderKind;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.operations.payment.ConfirmMCPaymentOperation;
import com.rssl.phizic.operations.payment.billing.EditInternetShopPaymentOperation;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Проводка платежей по MobileCheckout.
 *
 * @author bogdanov
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 */

public class OrderMobileCheckoutDocumentExecutor extends ShopDocumentInBlockExecutorBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static volatile OrderMobileCheckoutDocumentExecutor it = null;
	private static final Object LOCK = new Object();
	private final OperationFactory OPERATION_FACTORY = new OperationFactoryImpl(new RestrictionProviderImpl());

	/**
	 * @return инстанс класса
	 */
	public static OrderMobileCheckoutDocumentExecutor getIt()
	{
		if (it != null)
			return it;

		synchronized (LOCK)
		{
			if (it != null)
				return it;

			it = new OrderMobileCheckoutDocumentExecutor();
			return it;
		}
	}

	/**
	 * Автоматическое создание документа оплаты заказа.
	 * @param order заказ.
	 */
	public void createPayment(ShopOrder order) throws Exception
	{
		try
		{
			ActivePerson person = initPersonContext(order);

			//формирование платежа
			FieldValuesSource fieldValuesSource = getPaymentValuesSource(order, person);
			String formName = getFormName(order);
			ExternalShopDocumentSource source = new ExternalShopDocumentSource(order.getUuid(), fieldValuesSource, CreationType.internet, CreationSourceType.ordinary);
			BusinessDocument businessDocument = source.getDocument();
			((GateExecutableDocument)businessDocument).setExternalOwnerId(person.getClientId());
			businessDocument.setErmbPaymentType(ErmbPaymentType.SERVICE_PAYMENT.name());

			//проверка данных и сохранение платежа
			EditInternetShopPaymentOperation editOperation = OPERATION_FACTORY.create(EditInternetShopPaymentOperation.class, formName);
			editOperation.initialize(source, (Long) null);

			Metadata metadata = editOperation.getMetadata();
			FormProcessor<List<String>, StringErrorCollector> formProcessor = new FormProcessor<List<String>, StringErrorCollector>(
					fieldValuesSource, metadata.getForm(), new StringErrorCollector(), DocumentValidationStrategy.getInstance());

			if (!formProcessor.process())
			{
				log.error(formProcessor.getErrors().get(0));
				throw new BusinessLogicException("Ошибка валидации документа");
			}
			editOperation.updateDocument(formProcessor.getResult());
			editOperation.save();

			//отправка оферты
			ShopListenerConfig shopListenerConfig = ConfigFactory.getConfig(ShopListenerConfig.class);
			String smsText = order.getKind() == OrderKind.INTERNET_SHOP ? shopListenerConfig.getInternetOfferSms() : shopListenerConfig.getAirlineOfferSms();
			String amount = order.getAmount().getDecimal().toPlainString() + " " + CurrencyUtils.getCurrencySign(order.getAmount().getCurrency().getCode());
			smsText = smsText.replace("{0}", order.getReceiverName()).replace("{1}", order.getExternalId()).replace("{2}", amount).replace("..", ".");
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			mobileBankService.sendOfferMessageSMS(smsText, smsText, order.getId(), order.getPhone());
		}
		catch (GateException e)
		{
			log.error("Ошибка при отправке оферты.", e);
		}
		finally
		{
			clearPersonContext();
		}
	}

	/**
	 * Отправка документа оплаты заказа в биллинг
	 * @param order - заказ
	 */
	public void sendPayment(ShopOrder order) throws Exception
	{
		try
		{
			initPersonContext(order);
			ConfirmMCPaymentOperation confirmOperation = OPERATION_FACTORY.create(ConfirmMCPaymentOperation.class, getFormName(order));
			confirmOperation.initialize(new ExistingSource(order));
			confirmOperation.confirm();
		}
		finally
		{
			clearPersonContext();
		}
	}

	private FieldValuesSource getPaymentValuesSource(ShopOrder order, ActivePerson activePerson) throws BusinessException, BusinessLogicException
	{
		Card card = MobileBankManager.getCardByPhone(order.getPhone(), activePerson);
		if (card == null || StringHelper.isEmpty(card.getNumber()))
			throw new BusinessException("Не найдена карта для создания документа оплаты интернет-заказа через Mobile Checkout.");
		String cardNumber = card.getNumber();
		ExternalResourceLink link = externalResourceService.findLinkByNumber(activePerson.getLogin(), ResourceType.CARD, cardNumber);

		if (link == null)
		{
			link = externalResourceService.addCardLink(activePerson.getLogin(), card, null, ErmbProductSettings.get(activePerson.getId()), null, null);
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PaymentFieldKeys.ORDER_ID_KEY, order.getUuid());
		map.put(PaymentFieldKeys.FROM_RESOURCE_TYPE,  CardLink.class.getName());
		map.put(PaymentFieldKeys.FROM_ACCOUNT_SELECT, cardNumber);
		map.put(PaymentFieldKeys.FROM_RESOURCE_KEY,   link.getCode());

		return new MapValuesSource(map);
	}



	private String getFormName(ShopOrder order)
	{
		return order.getKind() == OrderKind.INTERNET_SHOP ? "ExternalProviderPayment" : "AirlineReservationPayment";
	}
}

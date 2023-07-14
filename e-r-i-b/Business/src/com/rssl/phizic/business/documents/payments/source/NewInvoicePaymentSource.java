package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.basket.invoiceSubscription.LightInvoiceSubscription;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.InvoicePayment;
import com.rssl.phizic.business.resources.external.ActiveCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 09.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Создание оплаты задолженности по инвойсу
 */
public class NewInvoicePaymentSource extends NewDocumentSource
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	private static BankDictionaryService bankDictionaryService = new BankDictionaryService();

	/**
	 * ctor
	 * @param invoice задолженность
	 */
	public NewInvoicePaymentSource(Invoice invoice) throws BusinessException, BusinessLogicException
	{
		if (invoice == null)
		{
			throw new IllegalArgumentException("Задолженность не может быть null");
		}

		document = createInvoicePayment(invoice);
		metadata = MetadataCache.getExtendedMetadata(document);

		initDocument(CreationType.internet, CreationSourceType.copy);

		// недостающие данные
		InvoicePayment payment = (InvoicePayment) document;
		payment.setOffice(payment.getDepartment());
	}

	private BusinessDocument createInvoicePayment(Invoice invoice) throws BusinessException, BusinessLogicException
	{
		Metadata basicMetadata = MetadataCache.getBasicMetadata(FormConstants.INVOICE_PAYMENT_FORM);
		FieldValuesSource source = createValueSource(invoice);
		InvoicePayment payment = (InvoicePayment) documentService.createDocument(basicMetadata, source, null);

		updatePayment(payment, invoice);

		return payment;
	}

	private void updatePayment(InvoicePayment payment, Invoice invoice) throws BusinessException
	{
		try
		{
			payment.setReceiverPointCode(invoice.getCodeRecipientBs());
			payment.setExtendedFields(invoice.getRequisitesAsList());
			payment.setState(new State("INITIAL")); // для метадаталоадера

			if (invoice.getCommission() != null)
			{
				Currency currency = MoneyUtil.getNationalCurrency();
				payment.setCommission(new Money(invoice.getCommission(), currency));
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	private FieldValuesSource createValueSource(Invoice invoice) throws BusinessException, BusinessLogicException
	{
		Map<String, String> fieldsMap = new HashMap<String, String>();

		fieldsMap.put("subscriptionAutopayId", invoice.getAutoPaySubscriptionId());
		fieldsMap.put("subscriptionId", String.valueOf(invoice.getInvoiceSubscriptionId()));
		fieldsMap.put("invoiceId", String.valueOf(invoice.getId()));
		fieldsMap.put("invoiceAutopayId", invoice.getAutoPayId());
		fieldsMap.put("invoiceStatus", invoice.getState().name());
		fieldsMap.put("invoiceDelayDate", DateHelper.formatDateToString(invoice.getDelayedPayDate()));
		fieldsMap.put("receiverName", invoice.getRecName());
		fieldsMap.put("nameService", invoice.getNameService());
		fieldsMap.put("receiverAccount", invoice.getRecAccount());
		fieldsMap.put("receiverINN", invoice.getRecInn());
		fieldsMap.put("receiverBIC", invoice.getRecBic());
		fieldsMap.put("subscriptionName", getLightSubscription(invoice.getInvoiceSubscriptionId()).getName());
		fieldsMap.put("recipient", String.valueOf(getLightSubscription(invoice.getInvoiceSubscriptionId()).getRecipientId()));

		fieldsMap.putAll(getBankFields(invoice));
		fieldsMap.putAll(getCardFields(invoice));

		return new MapValuesSource(fieldsMap);
	}

	private Map<String, String> getBankFields(Invoice invoice) throws BusinessException
	{
		ResidentBank bankInfo = bankDictionaryService.findByBIC(invoice.getRecBic());

		if (bankInfo == null)
		{
			throw new BusinessException("Не найден банк в справочнике с БИКом " + invoice.getRecBic());
		}

		Map<String, String> values = new HashMap<String, String>();
		String recCorAccount = invoice.getRecCorAccount();

		values.put("receiverCorAccount", StringHelper.isEmpty(recCorAccount) ? bankInfo.getAccount() : recCorAccount);
		values.put("receiverBankName", bankInfo.getName());

		return values;
	}

	private Map<String, String> getCardFields(Invoice invoice) throws BusinessException, BusinessLogicException
	{

		Map<String, String> values = new HashMap<String, String>();
		CardLink cardLink = getFromResource(invoice.getLoginId(), invoice.getCardNumber());
		if(cardLink == null)
			return values;

		if (!(new ActiveCardFilter().accept(cardLink.getCard())))
		{
			throw new BusinessLogicException("Оплата доступна только с Вашей активной карты.");
		}

		values.put("fromAccountSelect", cardLink.getNumber());
		values.put("fromAccountName", cardLink.getName());
		values.put("fromResourceCurrency", cardLink.getCurrency().getCode());
		values.put("fromResourceRest", cardLink.getRest().getDecimal().toString());
		values.put("fromResourceType", cardLink.getClass().getName());

		return values;
	}

	protected CardLink getFromResource(Long loginId, String number) throws BusinessException
	{
		try
		{
			return super.getFromResource(loginId, number);
		}
		catch (BusinessException e)
		{
			// в режиме взаимодействия через шину возможнет выбор карты
			if(BasketHelper.getBasketInteractMode() == BasketPaymentsListenerConfig.WorkingMode.esb)
				return null;

			throw e;
		}
	}

	private LightInvoiceSubscription getLightSubscription(Long subscriptionId) throws BusinessException
	{
		LightInvoiceSubscription subscription = invoiceSubscriptionService.getLightSubscriptionById(subscriptionId);

		if (subscription == null)
		{
			throw new BusinessException("Услуга по идентификатору " + subscriptionId + " не найдена.");
		}
		//возвращаем данные
		return subscription;
	}
}

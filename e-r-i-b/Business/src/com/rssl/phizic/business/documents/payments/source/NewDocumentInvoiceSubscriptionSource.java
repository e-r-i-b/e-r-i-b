package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Базовый класс создания документа по подписке на инвойсы
 * @author niculichev
 * @ created 30.09.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class NewDocumentInvoiceSubscriptionSource<T> extends NewDocumentSource
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final String OPERATION_CODE = "{VO99090}";

	/**
	 * Создание документа по сущности подписки на инвойсы
	 * @param source сущность подписки на инвойсы
	 * @return готовый документ
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected abstract BusinessDocument createDocument(T source) throws BusinessException, BusinessLogicException;

	protected void initialize(T source, CreationType creationType, CreationSourceType creationSourceType) throws BusinessLogicException, BusinessException
	{
		document = createDocument(source);
		metadata = MetadataCache.getExtendedMetadata(document);

		initDocument(creationType, creationSourceType);

		// недостающие данные
		JurPayment payment = (JurPayment) document;
		payment.setOffice(payment.getDepartment());
	}

	/**
	 * Источник значений полей по созданной подписке на получение инвойсов
	 * @param subscription подписка получения инвойсов
	 * @return Источник значений полей
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	protected Map<String, String> getInitialValues(InvoiceSubscription subscription) throws BusinessException
	{
		Map<String, String> values = new HashMap<String, String>();

		BillingServiceProvider provider = getServiceProvider(subscription.getRecipientId());
		// информация по поставщику
		values.put("receiverAccount", provider.getAccount());
		values.put("receiverName", provider.getName());
		values.put("receiverId", provider.getSynchKey().toString());
		values.put("recipient", provider.getId().toString());
		values.put("billingCode", provider.getBilling().getCode());
		values.put("nameOnBill", provider.getNameOnBill());
		values.put("bankDetails", Boolean.toString(provider.isBankDetails()));
		values.put("receiverINN", provider.getINN());
		values.put("receiverKPP", provider.getKPP());
		values.put("receiverBIC", provider.getBIC());
		values.put("receiverBankName", provider.getBankName());
		values.put("receiverCorAccount", provider.getCorrAccount());
		values.put("phoneNumber", provider.getPhoneNumber());

		CardLink cardLink = getFromResource(subscription.getLoginId(), subscription.getChargeOffCard());
		// информция по ресурсу списания
		values.put("fromAccountSelect", cardLink.getNumber());
		values.put("fromResourceType", cardLink.getClass().getName());
		values.put("fromAccountType", cardLink.getValue().getDescription());
		values.put("fromAccountName", cardLink.getName());
		values.put("fromResource", cardLink.getCode());
		values.put("fromResourceCurrency", cardLink.getCurrency().getCode());
		values.put("operationCode", getOperationCode(provider.getAccount(), cardLink.getNumber()));
		values.put("fromResourceLink", cardLink.getCode());

		// информация по периодичности
		values.put("autoSubType", AutoSubType.INVOICE.name());
		values.put("autoSubName", subscription.getName());
		values.put("autoSubEventType", subscription.getExecutionEventType().name());
		values.put("autoSubSumType", SumType.BY_BILLING.name());

		Calendar payDate = subscription.getPayDate();
		values.put("nextPayDateInvoice", DateHelper.formatDateToString(getNextPayDate(payDate, subscription.getExecutionEventType())));
		values.put("nextPayDayOfWeekInvoice", Integer.toString(payDate.get(Calendar.DAY_OF_WEEK)));  // TODO проверить корректность числа
		values.put("nextPayDayOfMonthInvoice", Integer.toString(payDate.get(Calendar.DAY_OF_MONTH)));
		values.put("nextPayMonthOfQuarterInvoice", Integer.toString(DateHelper.getQuarter(payDate)));

		return values;
	}

	protected Map<String, String> getInitialValues(CardPaymentSystemPaymentLongOffer claim) throws BusinessException
	{
		Map<String, String> values = new HashMap<String, String>();

		try
		{
			BillingServiceProvider provider = getServiceProvider(claim.getReceiverInternalId());
			// информация по поставщику
			values.put("receiverAccount", provider.getAccount());
			values.put("receiverName", provider.getName());
			values.put("receiverId", provider.getSynchKey().toString());
			values.put("recipient", provider.getId().toString());
			values.put("billingCode", provider.getBilling().getCode());
			values.put("nameOnBill", provider.getNameOnBill());
			values.put("bankDetails", Boolean.toString(provider.isBankDetails()));
			values.put("receiverINN", provider.getINN());
			values.put("receiverKPP", provider.getKPP());
			values.put("receiverBIC", provider.getBIC());
			values.put("receiverBankName", provider.getBankName());
			values.put("receiverCorAccount", provider.getCorrAccount());
			values.put("phoneNumber", provider.getPhoneNumber());

			CardLink cardLink = getFromResource(claim.getInternalOwnerId(), claim.getChargeOffCard());
			// информция по ресурсу списания
			values.put("fromAccountSelect", cardLink.getNumber());
			values.put("fromResourceType", cardLink.getClass().getName());
			values.put("fromAccountType", cardLink.getValue().getDescription());
			values.put("fromAccountName", cardLink.getName());
			values.put("fromResource", cardLink.getCode());
			values.put("fromResourceCurrency", cardLink.getCurrency().getCode());
			values.put("operationCode", getOperationCode(provider.getAccount(), cardLink.getNumber()));
			values.put("fromResourceLink", cardLink.getCode());

			// информация по периодичности
			values.put("autoSubType", AutoSubType.INVOICE.name());
			values.put("autoSubName", claim.getFriendlyName());
			values.put("autoSubEventType", claim.getExecutionEventType().name());
			values.put("autoSubSumType", claim.getSumType().name());

			Calendar payDate = claim.getNextPayDate();
			values.put("nextPayDateInvoice", DateHelper.formatDateToString(getNextPayDate(payDate, claim.getExecutionEventType())));
			values.put("nextPayDayOfWeekInvoice", Integer.toString(payDate.get(Calendar.DAY_OF_WEEK)));  // TODO проверить корректность числа
			values.put("nextPayDayOfMonthInvoice", Integer.toString(payDate.get(Calendar.DAY_OF_MONTH)));
			values.put("nextPayMonthOfQuarterInvoice", Integer.toString(DateHelper.getQuarter(payDate)));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		return values;
	}

	private Calendar getNextPayDate(Calendar payDate, ExecutionEventType eventType) throws BusinessException
	{
		switch (eventType)
		{
			case ONCE_IN_WEEK:
			{
				return DateHelper.getNearDateByWeek(payDate.get(Calendar.DAY_OF_WEEK));
			}
			case ONCE_IN_MONTH:
			{
				return DateHelper.getNearDateByMonth(payDate.get(Calendar.DAY_OF_MONTH));
			}
			case ONCE_IN_QUARTER:
			{
				return DateHelper.getNearDateByQuarter(DateHelper.getQuarter(payDate), payDate.get(Calendar.DAY_OF_MONTH));
			}
			default:
			{
				throw new BusinessException("Некорректные параметры подписки на инвойсы");
			}
		}
	}

	protected BillingServiceProvider getServiceProvider(Long ricipientId) throws BusinessException
	{
		ServiceProviderBase providerBase = serviceProviderService.findById(ricipientId);
		if(providerBase == null)
			throw new BusinessException("Не найден поставщик с id = " + ricipientId + " для создания автоподписки");

		if(!(providerBase instanceof BillingServiceProvider))
			throw new BusinessException("Поставщик с id = " + ricipientId + " не является биллинговым поставщиком.");

		BillingServiceProvider provider = (BillingServiceProvider) providerBase;
		if(!provider.isAutoPaymentSupported() || provider.getInvoiceAutoPayScheme() == null)
			throw new BusinessException("Поставщик с id = " + ricipientId + " не поддерживает создание подписки.");

		if(provider.getState() != ServiceProviderState.ACTIVE)
			throw new BusinessException("Поставщик с id = " + ricipientId + " не активный.");

		return provider;
	}

	private String getOperationCode(String receiverAccount, String fromAccountSelect)
	{
		if((StringHelper.isNotEmpty(receiverAccount) && (receiverAccount.substring(0,5) == "40820" || receiverAccount.substring(0,3) == "426"))
				|| (StringHelper.isNotEmpty(fromAccountSelect) && (fromAccountSelect.substring(0,5) == "40820" || fromAccountSelect.substring(0,3) == "426")))
			return OPERATION_CODE;

		return "";
	}
}
